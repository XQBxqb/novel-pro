package com.novel.file.controller;

import com.aliyun.oss.OSS;
import com.novel.api.controller.FileControllerMngApi;
import com.novel.core.consts.HttpStatusCode;
import com.novel.core.dto.FilePartDto;
import com.novel.core.res.RestRes;
import com.novel.core.utils.FileMD5Utils;
import com.novel.file.centor.FileOSSCentor;
import com.novel.file.service.FileService;
import io.swagger.models.auth.In;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.yarn.webapp.hamlet.Hamlet;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author 昴星
 * @date 2023-10-21 17:11
 * @explain
 */
@RestController
@RequiredArgsConstructor
@Slf4j
public class FileController implements FileControllerMngApi {
    private final OSS ossClient;

    private final FileOSSCentor fileOSSCentor;

    private final Configuration configuration;

    private final FileSystem fs;
    private final FileService fileService;

    @Override
    public RestRes<FilePartDto> partFile(MultipartFile multipartFile, String fileName, Integer index) {
        if (StringUtils.isBlank(fileName) || multipartFile.isEmpty())
            return RestRes.fileResp(HttpStatusCode.FILE_PART_FILE_LOAD_MISSING_CONDITIONS.code, null);
        RestRes<FilePartDto> filePartDtoRestRes = null;
        try {
            filePartDtoRestRes = fileService.uploadPartFile(multipartFile, fileName, index);
        } catch (Exception e) {
            return RestRes.fileResp(HttpStatusCode.File_PART_LOAD_ERROR.code, null);
        }
        return filePartDtoRestRes;
    }

    @Override
    public RestRes<FilePartDto> firstPartFile(MultipartFile multipartFile, Integer totalNum, String fileName) {

        if (StringUtils.isBlank(fileName) || multipartFile.isEmpty() || totalNum.equals(0))
            return RestRes.fileResp(HttpStatusCode.FILE_PART_FILE_LOAD_MISSING_CONDITIONS.code, null);
        RestRes<FilePartDto> filePartDtoRestRes = null;
        try {
            filePartDtoRestRes = fileService.uploadFirstPartFile(multipartFile, fileName, totalNum);
        } catch (Exception e) {
            return RestRes.fileResp(HttpStatusCode.File_PART_LOAD_ERROR.code, null);
        }
        return filePartDtoRestRes;
    }

    @Override
    public RestRes<Boolean> innerMatchFilePartMD5(String fileName, String MD5) {
        return null;
    }

    @Override
    public RestRes<Void> mergePartFile(String fileDir) {
        try {
            fileService.merge(fileDir);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void hello(MultipartFile multipartFile) throws IOException {

        /*log.info("------uploadToOSS------");
        String bucketName = fileOSSCentor.getBucketName();
        File file = new File("text");
        FileInputStream fileInputStream = new FileInputStream(file);
        ossClient.putObject(bucketName,"example.txt",fileInputStream);
        fileInputStream.close();*/


        /*log.info("--------downFromOSS---------");
        OSSObject object = ossClient.getObject(fileOSSCentor.getBucketName(), "example.txt");

        InputStream objectContent = object.getObjectContent();
        int len;
        byte[] bytes=new byte[1024];
        while((len=objectContent.read(bytes))!=-1){
            System.out.println(new String(bytes,0,len));
        }
        ossClient.shutdown();*/


        /*if(multipartFile==null) return;
        log.info("-----MultipartFile UpLoadTo OSS----");
        InputStream inputStream = multipartFile.getInputStream();
        ossClient.putObject(fileOSSCentor.getBucketName(),"testFile.txt",inputStream);

        OSSObject ossObject = ossClient.getObject(fileOSSCentor.getBucketName(), "testFile.txt");
        InputStream objectContent = ossObject.getObjectContent();
        int len;
        byte[] bytes=new byte[1024];
        while((len=objectContent.read(bytes))!=-1){
            System.out.println(new String(bytes,0,len));
        }*/


        /*log.info("----HDFS UPLOAD-------");
        String uri = "hdfs://192.168.98.120:8020/user/hdfsfile.txt";
        File file = new File("text");
        InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
        Configuration configuration = new Configuration();
        FileSystem fs = FileSystem.get(URI.create(uri), configuration);
        OutputStream fsDataOutputStream = fs.create(new Path("/user/helloHDFS1.txt"));
        IOUtils.copyBytes(inputStream,fsDataOutputStream,configuration);
        inputStream.close();
        fsDataOutputStream.close();*/

        /*log.info("-----HDFS DOWNLOAD------");
        Configuration configuration = new Configuration();
        FileSystem fs = FileSystem.get(URI.create("hdfs://192.168.98.120:8020/user/helloHDFS.txt"),configuration);
        InputStream fsDataInputStream = fs.open(new Path("/user/helloHDFS.txt"));
        int len;
        byte[] bytes=new byte[1025];
        while((len=fsDataInputStream.read(bytes))!=-1){
            System.out.println(new String(bytes,0,len));
        }*/

        /*log.info("----HDFS FILE PART MD5 TEST-------");
        String uri = "hdfs://192.168.98.120:8020/user/hdfsfile.txt";
        File file = new File("text");
        InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
        try {
            String calculateMD5 = FileMD5Utils.calculateMD5(inputStream);

            Configuration configuration = new Configuration();
            FileSystem fs = FileSystem.get(URI.create(uri), configuration);
            FSDataInputStream fsDataInputStream = fs.open(new Path("/user/helloHDFS.txt"));

            String md5 = FileMD5Utils.calculateMD5(fsDataInputStream);
            if (md5.equals(calculateMD5) == true) {
                System.out.println("yes");
            } else {
                System.out.println("no");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }*/

        /*List<FileStatus> list = Arrays.asList(fs.listStatus(new Path("hdfs://192.168.98.120:8020/user/")));
        list.stream().parallel().forEach(t->{
            String name = t.getPath().getName();
            System.out.println(name);
        });*/

        /*log.info("--------  PART FILE ------");
        byte[] bytes = new String("小王爱吃饭").getBytes();
        StringBuilder stringBuilder=new StringBuilder();
        for(int i=0;i<1030;i++){
            stringBuilder.append(new String(bytes,0,bytes.length,StandardCharsets.UTF_8));
        }
        byte[] allBytes = new String(stringBuilder).getBytes(StandardCharsets.UTF_8);
        OutputStream outputStream = new FileOutputStream(new File("test.txt"));
        outputStream.write(allBytes);*/

        /*log.info(("-------- UPLOAD PART FILE TO HDFS"));
        byte[] bytes=new byte[1024];
        File file = new File("test.txt");
        FileInputStream inputStream = new FileInputStream(file);
        int len;
        int index=-1;
        while((len=inputStream.read(bytes))!=-1){
            index++;
            InputStream partFileInputStream = new ByteArrayInputStream(bytes, 0, len);
            FSDataOutputStream fsDataOutputStream = fs.create(new Path("/domoOSS/demoOSS" + index));
            IOUtils.copyBytes(partFileInputStream,fsDataOutputStream,configuration);
            partFileInputStream.close();
            fsDataOutputStream.close();
        }*/

        log.info("---------- check data consistent --------");
        FileInputStream fileInputStream = new FileInputStream(new File("test.txt"));

        byte[] bytes = new byte[1024];
        StringBuffer builder = new StringBuffer();
        Arrays.asList(fs.listStatus(new Path("/domoOSS")))
              .stream()
              .sorted(Comparator.comparing(t -> t.getPath()
                                                 .getName()))
              .forEach(t -> downLoadPartStream(bytes, builder, t));
        byte[] mergeBytes = new String(builder).getBytes(StandardCharsets.UTF_8);
        InputStream mergeStream = new ByteArrayInputStream(mergeBytes);
        try {
            if (FileMD5Utils.calculateMD5(fileInputStream)
                            .equals(FileMD5Utils.calculateMD5(mergeStream))) log.info("right");
            else log.info("error");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void downLoadPartStream(byte[] bytes, StringBuffer builder, FileStatus t) {
        Path path = t.getPath();
        try {
            FSDataInputStream inputStream = fs.open(path);
            int len;
            while ((len = inputStream.read(bytes)) != -1) {
                builder.append(new String(bytes, 0, len, StandardCharsets.UTF_8));
            }
            inputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ;
    }

    public static void main(String[] args) throws IOException {

    }
}
