package com.kithy.webfluxsampleapp.sandbox;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MultiThreadSquareCalculator {

    private ExecutorService executor = Executors.newFixedThreadPool(2);

    public Future<Integer> calculate(Integer input) {
        return executor.submit(() -> {
            Thread.sleep(1000);
            return input * input;
        });
    }

    public void shutdown() {
        System.exit(0);
    }
}
