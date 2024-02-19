package com.novel.file.config;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.net.URI;

/**
 * @author 昴星
 * @date 2023-10-24 15:01
 * @explain
 */
@org.springframework.context.annotation.Configuration
public class FileSystemConfig {
    @Bean
    public Configuration configuration(){
        return new Configuration();
    }
    @Bean
    public FileSystem fileSystem() throws IOException {
        Configuration configuration = configuration();
        return FileSystem.get(URI.create("hdfs://192.168.98.120:8020/user/helloHDFS.txt"), configuration);
    }
}
