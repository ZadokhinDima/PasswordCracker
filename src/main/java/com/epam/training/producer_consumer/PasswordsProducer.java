package com.epam.training.producer_consumer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;

public class PasswordsProducer implements Runnable {

    private BlockingQueue<String> passwordsToCheck;

    private CountDownLatch countDownLatch;

    private String lastPassword;


    public PasswordsProducer(final BlockingQueue<String> passwordsToCheck, final CountDownLatch countDownLatch) {
        this.passwordsToCheck = passwordsToCheck;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        lastPassword = "";
        while (countDownLatch.getCount() > 0) {
            final String newPassword;
            synchronized(this) {
                newPassword = generateNextPassword(lastPassword);
                lastPassword = newPassword;
            }
            putToTaskQueue(newPassword);

        }
    }

    private String generateNextPassword(String current) {
        if (current.isEmpty()) {
            return "a";
        } else if (current.endsWith("z")) {
            String prefix = current.substring(0, current.length() - 1);
            return generateNextPassword(prefix) + "a";
        } else {
            char lastCharacter = current.charAt(current.length() - 1);
            lastCharacter++;
            return current.substring(0, current.length() - 1) + lastCharacter;
        }
    }

    private void putToTaskQueue(String password) {
        try {
            passwordsToCheck.put(password);
        } catch (InterruptedException e) {
            System.out.println("Stopped producer thread");
        }
    }
}
