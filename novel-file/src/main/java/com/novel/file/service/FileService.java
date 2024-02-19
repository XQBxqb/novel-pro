package com.novel.file.service;

import com.novel.core.dto.FilePartDto;
import com.novel.core.res.RestRes;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author 昴星
 * @date 2023-10-23 8:31
 * @explain
 */
public interface FileService {
    public  RestRes<FilePartDto> uploadPartFile (MultipartFile multipartFile, String fileName, Integer index) throws Exception;

    public  RestRes<FilePartDto> uploadFirstPartFile(MultipartFile multipartFile,String fileName,Integer totalNum) throws Exception;

    public RestRes<Void> merge(String fileDir) throws IOException;

    public RestRes<Boolean> innerMatchFilePartMD5(String fileName, String MD5) throws Exception;
}
