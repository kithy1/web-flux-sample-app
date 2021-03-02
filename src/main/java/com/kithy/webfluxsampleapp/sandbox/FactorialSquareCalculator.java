package com.kithy.webfluxsampleapp.sandbox;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class FactorialSquareCalculator extends RecursiveTask<Integer> {

    private Integer n;

    public FactorialSquareCalculator(Integer n) {
        this.n = n;
    }

    @Override
    protected Integer compute() {
        if(n <= 1) {
            return n;
        }

        FactorialSquareCalculator calculator = new FactorialSquareCalculator(n - 1);

        calculator.fork();
        // we achieve recursiveness by creating a new instance of FactorialSquareCalculator within compute().
        // By calling fork(), a non-blocking method, we ask ForkJoinPool to initiate the execution of this subtask.

        return n * n + calculator.join();
        // The join() method will return the result from that calculation, to which we add the square of the number we are currently visiting.
    }

    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();

        FactorialSquareCalculator calculator = new FactorialSquareCalculator(4);

        forkJoinPool.execute(calculator);

        Integer compute = calculator.compute();
        System.out.println(compute);
    }
}
