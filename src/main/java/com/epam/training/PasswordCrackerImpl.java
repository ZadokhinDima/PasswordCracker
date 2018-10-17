package com.epam.training;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class PasswordCrackerImpl implements PasswordCracker {

    private String desiredHash;

    private final int MAX_QUEUE_SIZE = 1000;

    private CountDownLatch countDownLatch = new CountDownLatch(1);

    private HashCalculator hashCalculator = new HashCalculator();

    private ThreadPoolExecutor executor =
            new ThreadPoolExecutor(4, 4, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(MAX_QUEUE_SIZE));

    private String result;

    public PasswordCrackerImpl(final String desiredHash) {
        this.desiredHash = desiredHash;
    }

    public String crackPassword() {
        checkPassword("");
        try {
            executor.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void setDesiredHash(final String desiredHash) {
        this.desiredHash = desiredHash;
    }

    private void checkPassword(final String password) {
        System.out.println(password);
        if (hashCalculator.hash(password).equals(desiredHash)) {
            saveResult(password);
        }
        LETTERS.chars().mapToObj(i -> (char)i).forEach(letter -> executor.submit(() -> checkPassword(password + letter.toString())));
    }


    private synchronized void saveResult(final String result) {
        this.result = result;
        executor.shutdownNow();
    }

}
