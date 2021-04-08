package com.kithy.webfluxsampleapp.sandbox;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.stream.Stream;

public class FileReaderUtils {

    @Autowired
    ResourceLoader resourceLoader;


    public Stream<String> readFile() throws IOException {

        Resource resource = loadEmployeesWithResourceLoader();
        URL url = resource.getURL();

        String content = new String(Files.readAllBytes(Path.of(String.valueOf(url))));
        String[] split = content.split(";");
        for (String s : split) {
            System.out.println(s);
        }
        return Arrays.stream(split);
    }

    public static void main(String[] args) throws IOException {
        FileReaderUtils fru = new FileReaderUtils();
        fru.readFile();
    }

    public Resource loadEmployeesWithResourceLoader() {
        return resourceLoader.getResource(
                "classpath:messagePatterns/employees.dat");
    }


}
