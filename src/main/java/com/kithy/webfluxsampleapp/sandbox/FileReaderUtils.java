package com.kithy.webfluxsampleapp.sandbox;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

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
        fru.readFile("src/main/resources/messagePatterns/deliveryPattern");
    }




}
