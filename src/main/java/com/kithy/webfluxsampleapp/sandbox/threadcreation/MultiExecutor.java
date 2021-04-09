package com.kithy.webfluxsampleapp.sandbox.threadcreation;

import java.util.List;

public class MultiExecutor {

    private List<Runnable> tasks;

    public MultiExecutor(List<Runnable> tasks) {
        this.tasks = tasks;
    }

    public void executeAll(){
        this.tasks.forEach(runnable -> {
            Thread thread = new Thread(runnable);
            thread.start();
        });
    }
}
