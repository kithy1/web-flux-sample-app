package com.kithy.webfluxsampleapp.sandbox.resource;

import com.kithy.webfluxsampleapp.sandbox.FileReaderUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
public class ResourceConfig {

    @Value("classpath:textFiles/test.txt")
    Resource resource;

    @Bean
    public FileReaderUtils fileReaderUtils(){
        FileReaderUtils fileReaderUtils = new FileReaderUtils();
        fileReaderUtils.setResource(resource);
        return fileReaderUtils;
    }
}
