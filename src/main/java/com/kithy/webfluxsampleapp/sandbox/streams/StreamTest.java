package com.kithy.webfluxsampleapp.sandbox.streams;

import java.util.IntSummaryStatistics;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamTest {

    public static void main(String[] args) {
        Stream<String> stringStream = Stream.of("red", "blue", "green");

        //Map<Integer, List<String>> collect = stringStream.collect(Collectors.groupingBy(String::length, Collectors.toList()));

        Map<String, IntSummaryStatistics> collect = stringStream.collect(Collectors.groupingBy(Function.identity(), Collectors.mapping(String::length, Collectors.summarizingInt(value -> Integer.sum(0, value)))));



        collect.forEach((s, integers) -> System.out.println("s: " + s + " integers: " + integers));


    }
}
