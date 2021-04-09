package com.kithy.webfluxsampleapp.sandbox.threadcreation;

public class MyRunnable1 implements Runnable{
    @Override
    public void run() {
        System.out.println("I'm MyRunnable1, thread name: " + Thread.currentThread().getName());
    }
}
