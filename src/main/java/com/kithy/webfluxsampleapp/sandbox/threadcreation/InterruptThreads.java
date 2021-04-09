package com.kithy.webfluxsampleapp.sandbox.threadcreation;

import java.math.BigInteger;

public class InterruptThreads {
    public static void main(String[] args) {
        Thread thread = new Thread(new LongComputation(new BigInteger("20000"), new BigInteger("10000000")));
        thread.setDaemon(true); // powoduje że wątek jest traktowany jako Daemon i aplikacja skończy działanie nie czekając na jego zakończenie
        thread.start();
        thread.interrupt();
    }

    private static class LongComputation implements Runnable {
        private BigInteger base;
        private BigInteger power;

        public LongComputation(BigInteger base, BigInteger power) {
            this.base = base;
            this.power = power;
        }

        @Override
        public void run() {
            System.out.println(base+"^"+power+"="+ pow(base, power));
        }

        private BigInteger pow(BigInteger base, BigInteger power) {
            BigInteger result = BigInteger.ONE;

            for (BigInteger i = BigInteger.ZERO; i.compareTo(power) !=0 ; i = i.add(BigInteger.ONE)) {
//                if(Thread.currentThread().isInterrupted()) {
//                    System.out.println("Prematurely interrupted computation");
//                    return BigInteger.ZERO;
//                }
                result = result.multiply(base);
            }
            return result;
        }
    }



}


