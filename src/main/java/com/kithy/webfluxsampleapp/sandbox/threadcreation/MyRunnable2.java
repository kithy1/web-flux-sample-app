package com.kithy.webfluxsampleapp.sandbox.threadcreation;

public class MyRunnable2 implements Runnable{
    @Override
    public void run() {
        System.out.println("i'm MyRunnable2, thread name: " + Thread.currentThread().getName());
    }
}
