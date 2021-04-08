package com.kithy.webfluxsampleapp.sandbox;

import com.opencsv.CSVReader;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Test {
    public static void main(String[] args) {
        LocalDateTime now = LocalDateTime.now();
        String s = now.toString();
        System.out.println(s);
        System.out.println("".length());
    }
}
