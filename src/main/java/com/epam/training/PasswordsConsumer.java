package com.epam.training;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;

public class PasswordsConsumer implements Runnable {

    private static final String DESIRED_HASH = "4fd0101ea3d0f5abbe296ef97f47afec";

    private BlockingQueue<String> passwordsToCheck;

    private HashCalculator hashCalculator = new HashCalculator();

    private CountDownLatch countDownLatch;

    public PasswordsConsumer(final BlockingQueue<String> passwordsToCheck, final CountDownLatch countDownLatch) {
        this.passwordsToCheck = passwordsToCheck;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        while (countDownLatch.getCount() > 0) {
            final String password = getNextPasswordToCheck();
            final String hash = hashCalculator.hash(password);
            if (hash.equals(DESIRED_HASH)) {
                countDownLatch.countDown();
                System.out.println(password + " is correct");
                return;
            }
        }
    }

    private String getNextPasswordToCheck() {
        try {
            return passwordsToCheck.take();
        } catch (InterruptedException e) {
            System.out.println("Interrupted");
            throw new RuntimeException(e);
        }
    }
}
