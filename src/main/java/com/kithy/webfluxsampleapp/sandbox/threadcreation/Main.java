package com.kithy.webfluxsampleapp.sandbox.threadcreation;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        MyRunnable1 myRunnable1 = new MyRunnable1();
        MyRunnable2 myRunnable2 = new MyRunnable2();

        List<Runnable> runnableList = new ArrayList<>();
        runnableList.add(myRunnable1);
        runnableList.add(myRunnable2);

        MultiExecutor multiExecutor = new MultiExecutor(runnableList);

        multiExecutor.executeAll();
    }


}
