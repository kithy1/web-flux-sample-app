package com.kithy.webfluxsampleapp.sandbox;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;

public class FileReaderUtils {

    @Autowired
    ResourceLoader resourceLoader;

    Resource resource;

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    @PostConstruct
    public void readFromResource() throws IOException {
        File file = resource.getFile();
        String s = new String(Files.readAllBytes(file.toPath()));
        System.out.println(s);
    }


    public Stream<String> readFile(String path) throws IOException {

        String content = new String(Files.readAllBytes(Paths.get(path)));
        String[] split = content.split(";");
        for (String s : split) {
            System.out.println(s);
        }
        return Arrays.stream(split);
    }

    public static void main(String[] args) throws IOException {
        FileReaderUtils fru = new FileReaderUtils();
//        fru.readFile("src/main/resources/messagePatterns/deliveryPattern");
        fru.readFromResource();
    }




}
