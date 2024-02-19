package com.novel.api.controller;

import com.novel.core.dto.FilePartDto;
import com.novel.core.res.RestRes;
import com.novel.core.router.RouterMapping;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.OutputBuffer;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author 昴星
 * @date 2023-10-21 17:12
 * @explain
 */

@RequestMapping(RouterMapping.FILE_API)
public interface FileControllerMngApi {
    @PostMapping("/hello")
    public void hello(@RequestPart("file") MultipartFile file) throws IOException;

    @PostMapping("/upload/partFile")
    public RestRes<FilePartDto> partFile(@RequestPart("file") MultipartFile multipartFile,
                                         @RequestParam("fileName") String fileName,
                                         @RequestParam("index") Integer index);

    @PostMapping("/upload/firstPartFile")
    public RestRes<FilePartDto> firstPartFile(@RequestPart("file") MultipartFile multipartFile,
                                              @RequestParam("totalNum")  Integer totalNum,
                                              @RequestParam("fileName") String fileName);

    @PostMapping("/inner/matchFilePart")
    public RestRes<Boolean> innerMatchFilePartMD5(@RequestParam("fileName") String fileName,
                                                  @RequestParam("MD5") String MD5);

    @PostMapping("/mergePartFile")
    public RestRes<Void> mergePartFile(@RequestParam("fileDir") String fileDir);
}
