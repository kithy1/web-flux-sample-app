package com.kithy.webfluxsampleapp.sandbox.threadcreation;

import java.math.BigInteger;

public class ComplexCalculation {

    public static void main(String[] args) throws InterruptedException {
        BigInteger result = calculateResult(new BigInteger("2"), new BigInteger("2"), new BigInteger("3"), new BigInteger("2"));
        System.out.println(result);
    }

    public static BigInteger calculateResult(BigInteger base1, BigInteger power1, BigInteger base2, BigInteger power2) throws InterruptedException {
        BigInteger result;
        PowerCalculatingThread p1 = new PowerCalculatingThread(base1, power1);
        PowerCalculatingThread p2 = new PowerCalculatingThread(base2, power2);
        Thread thread1 = new Thread(p1);
        Thread thread2 = new Thread(p2);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();

        result = p1.getResult().add(p2.getResult());
        return result;
    }

    private static class PowerCalculatingThread extends Thread {
        private BigInteger result = BigInteger.ONE;
        private BigInteger base;
        private BigInteger power;

        public PowerCalculatingThread(BigInteger base, BigInteger power) {
            this.base = base;
            this.power = power;
        }

        @Override
        public void run() {
            for (BigInteger i = BigInteger.ZERO; i.compareTo(power) !=0 ; i = i.add(BigInteger.ONE)) {
                result = result.multiply(base);
            }
            System.out.println(result);
        }

        public BigInteger getResult(){
            return result;
        }
    }
}
