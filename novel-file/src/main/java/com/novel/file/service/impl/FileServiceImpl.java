package com.novel.file.service.impl;

import com.aliyun.oss.OSS;
import com.novel.core.consts.HttpStatusCode;
import com.novel.core.consts.RedisConsts;
import com.novel.core.dto.FilePartDto;
import com.novel.core.res.RestRes;
import com.novel.core.utils.FileMD5Utils;
import com.novel.file.centor.FileOSSCentor;
import com.novel.file.service.FileService;
import com.novel.file.stream.Eithor;
import lombok.RequiredArgsConstructor;
import org.apache.commons.math3.util.Pair;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.yarn.webapp.hamlet.Hamlet;
import org.redisson.Redisson;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RLock;
import org.redisson.api.RMap;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author 昴星
 * @date 2023-10-23 8:31
 * @explain
 */
@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    private final OSS ossClient;

    private final Redisson redisson;
    private final FileSystem fs;
    private final Configuration configuration;
    private final FileOSSCentor fileOSSCentor;

    @Override
    public RestRes<FilePartDto> uploadPartFile(MultipartFile multipartFile, String fileName, Integer index) throws Exception {
        InputStream inputStream = multipartFile.getInputStream();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        org.apache.commons.io.IOUtils.copy(inputStream, baos);
        byte[] byteArray = baos.toByteArray();
        BufferedInputStream copyInputStream = new BufferedInputStream(new ByteArrayInputStream(byteArray));
        inputStream = new ByteArrayInputStream(baos.toByteArray());
        OutputStream fsDataOutputStream = null;
        try {
            Path path = new Path(fileName);
            fsDataOutputStream = fs.create(path);
            IOUtils.copyBytes(inputStream, fsDataOutputStream, configuration);
        } finally {
            baos.close();
            if (fsDataOutputStream != null) fsDataOutputStream.close();
            inputStream.close();
        }
        String fileMD5 = FileMD5Utils.calculateMD5(copyInputStream);
        RLock lock = redisson.getLock(RedisConsts.REDIS_FILE_PART_LOCK);
        lock.lock();
        Long num = null;
        try {
            RMap<String, String> map = redisson.getMap(RedisConsts.REDIS_FILE_PART_MAP);
            map.put(fileName, fileMD5);
            RAtomicLong atomicLong = redisson.getAtomicLong(RedisConsts.REDIS_FILE_PART_NUM);
            num = atomicLong.decrementAndGet();
        } finally {
            lock.unlock();
            copyInputStream.close();
        }
        int indexOf = fileName.lastIndexOf("/");
        String fileDir = fileName.substring(0, indexOf + 1);
        if (num == 0) return checkMergeCondition(fileDir);
        return RestRes.fileResp(String.valueOf(HttpStatus.OK.value()), null);
    }

    @Override
    public RestRes<FilePartDto> uploadFirstPartFile(MultipartFile multipartFile, String fileName, Integer totalNum) throws Exception {
        RLock lock = redisson.getLock(RedisConsts.REDIS_FILE_PART_LOCK);
        lock.lock();
        try {
            RAtomicLong atomicLong = redisson.getAtomicLong(RedisConsts.REDIS_FILE_PART_NUM);
            atomicLong.set(totalNum);
        } finally {
            lock.unlock();
        }

        InputStream orignInputStream = multipartFile.getInputStream();

        OutputStream fsDataOutputStream = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        org.apache.commons.io.IOUtils.copy(orignInputStream, baos);
        byte[] bytes = baos.toByteArray();
        InputStream copyInputStream = new ByteArrayInputStream(bytes);
        InputStream inputStream = new ByteArrayInputStream(bytes);
        try {
            fsDataOutputStream = fs.create(new Path(fileName));
            IOUtils.copyBytes(inputStream, fsDataOutputStream, configuration);
        } finally {
            orignInputStream.close();
            fsDataOutputStream.close();
            inputStream.close();
            baos.close();
        }
        lock = redisson.getLock(RedisConsts.REDIS_FILE_PART_LOCK);

        lock.lock();
        try {
            RMap<String, String> map = redisson.getMap(RedisConsts.REDIS_FILE_PART_MAP);
            map.put(fileName, FileMD5Utils.calculateMD5(copyInputStream));
            RAtomicLong atomicLong = redisson.getAtomicLong(RedisConsts.REDIS_FILE_PART_NUM);
            atomicLong.decrementAndGet();
        } finally {
            lock.unlock();
            copyInputStream.close();
        }
        return RestRes.fileResp(String.valueOf(HttpStatus.OK.value()), null);
    }

    @Override
    public RestRes<Void> merge(String fileDir) throws IOException {
        StringBuilder builder = new StringBuilder();
        byte[] bytes = new byte[1024];
        Arrays.asList(fs.listStatus(new Path(fileDir)))
              .stream()
              .sorted(Comparator.comparing(t -> t.getPath()
                                                 .getName()))
              .collect(Collectors.toList())
              .forEach(t -> {
                  Path path = t.getPath();
                  try {
                      FSDataInputStream inputStream = fs.open(path);
                      int len;
                      while ((len = inputStream.read(bytes)) != -1) {
                          builder.append(new String(bytes, 0, len, StandardCharsets.UTF_8));
                      }
                  } catch (IOException e) {
                      throw new RuntimeException(e);
                  }
                  ;
              });
        InputStream inputStream = new ByteArrayInputStream(builder.toString()
                                                                  .getBytes());
        ossClient.putObject(fileOSSCentor.getBucketName(), fileDir + ".txt", inputStream);
        return RestRes.ok();
    }

    @Override
    public RestRes<Boolean> innerMatchFilePartMD5(String fileName, String MD5) throws Exception {
        FSDataInputStream fsDataInputStream = fs.open(new Path(fileName));
        String calculateMD5 = FileMD5Utils.calculateMD5(fsDataInputStream);
        return RestRes.ok(MD5.equals(calculateMD5));
    }

    /**
     * @param fileDir 合并文件所在的目录
     * @description: 在合并文件所在目录进行数据一致性检查，这里使用了并行流增强效率，又使用了Eithor来捕捉异常，
     * 防止stream出错而中断流处理，Eithor的Right又包含了异常信息和异常流文件，所以能够对所有异常文件（包含数据不匹配，
     * 流异常文件）进行删除；
     * 测试-文件块数据一致测试
     * 文件块数据不一致测试
     * 文件块处理中流异常测试
     * 文件块数据不一致以及流异常测试
     * @author: 昴星
     */
    private RestRes<FilePartDto> checkMergeCondition(String fileDir) throws Exception {
        RLock lock = redisson.getLock(RedisConsts.REDIS_FILE_PART_LOCK);
        lock.lock();
        List<String> errorFilePart = new ArrayList<>();
        try {
            RMap<String, String> map = redisson.getMap(RedisConsts.REDIS_FILE_PART_MAP);

            List<FileStatus> list = Arrays.asList(fs.listStatus(new Path(fileDir)));

            List<? extends Eithor<? super Boolean, Pair<FileStatus, ? super RuntimeException>>> eithorErrorList =
                    list.stream()
                        .parallel()
                        .map(f -> Eithor.mapToEithor(f, t -> {
                            Path path = t.getPath();
                            String name = fileDir + path.getName();
                            String md5 = map.get(name);
                            FSDataInputStream inputStream = null;
                            try {
                                inputStream = fs.open(path);
                                if (!md5.equals(FileMD5Utils.calculateMD5(inputStream))) {
                                    errorFilePart.add(name);
                                }
                            } catch (
                                    Exception exception) {
                                throw new RuntimeException(exception);
                            } finally {
                                map.remove(name);
                            }
                            return true;
                        }))
                        .filter(t -> Eithor.mapToRight(t))
                        .collect(Collectors.toList());

            List<String> exceptionFile = eithorErrorList.stream()
                                                        .map(t -> t.getRight())
                                                        .collect(Collectors.toList())
                                                        .stream()
                                                        .map(t -> t.getFirst())
                                                        .collect(Collectors.toList())
                                                        .stream()
                                                        .map(t -> fileDir + t.getPath()
                                                                             .getName())
                                                        .collect(Collectors.toList());

            if (exceptionFile != null && exceptionFile.size() != 0) exceptionFile.addAll(exceptionFile);

            exceptionFile.stream()
                         .forEach(t -> {
                             Path path = new Path(fileDir + t);
                             try {
                                 fs.delete(path);
                             } catch (IOException e) {
                                 throw new RuntimeException(e);
                             }
                         });

            if (map.size() > 0) {
                map.entrySet()
                   .stream()
                   .forEach(t -> {
                       errorFilePart.add(t.getKey());
                   });
            }
        } finally {
            lock.unlock();
        }
        if (errorFilePart.size() != 0) {
            lock.lock();
            RestRes<FilePartDto> res = null;
            try {
                RAtomicLong atomicLong = redisson.getAtomicLong(RedisConsts.REDIS_FILE_PART_NUM);
                atomicLong.addAndGet(errorFilePart.size());
                res = RestRes.fileResp(HttpStatusCode.FILE_PART_MISSING.code, FilePartDto.builder()
                                                                                         .list(errorFilePart)
                                                                                         .build());
                return res;
            } finally {
                lock.unlock();
            }
        }
        return RestRes.fileResp(HttpStatusCode.OK.code, null);
    }

}
