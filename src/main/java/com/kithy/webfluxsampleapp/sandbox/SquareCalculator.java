package com.kithy.webfluxsampleapp.sandbox;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SquareCalculator {

    private ExecutorService executor = Executors.newSingleThreadExecutor();
    // the basic newSingleThreadExecutor(), gives us an ExecutorService capable of handling a single thread at a time.

    public Future<Integer> calculate(Integer input){
        return executor.submit(() -> { // Callable interface call() method
            Thread.sleep(1000);
            return input * input;
        });
        // submit() will take care of starting the task and return a FutureTask object, which is an implementation of the Future interface.
    }

    public void shutdown(){
        System.exit(0);
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Future<Integer> future = new SquareCalculator().calculate(10);

        while (!future.isDone()) {
            // Future.isDone() tells us if the executor has finished processing the task.
            // If the task is completed, it will return true otherwise, it returns false.
            System.out.println("Calculating...");
            Thread.sleep(300);
        }

        Integer result = future.get();
        // this method blocks the execution until the task is complete, but in our example,
        // this won't be an issue since we'll check first if the task is completed by calling isDone().
        // The difference between get(long, TimeUnit) and get(), is that the former will throw a TimeoutException if the task doesn't return before the specified timeout period.
        System.out.println(result);

        future = new SquareCalculator().calculate(4);

        boolean canceled = future.cancel(true);
        // Suppose we've triggered a task but, for some reason, we don't care about the result anymore.
        // We can use Future.cancel(boolean) to tell the executor to stop the operation and interrupt its underlying thread
        // if we try to call get() from that instance, after the call to cancel(), the outcome would be a CancellationException.
        // Future.isCancelled() will tell us if a Future was already canceled.
    }
}
