package com.epam.training.simple_solution;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;

import com.epam.training.HashCalculator;
import com.epam.training.PasswordCracker;

public class SimpleMultythreadedCracker implements PasswordCracker {

    private static final String DESIRED_HASH = "4fd0101ea3d0f5abbe296ef97f47afec";
    private static final int THREADS_COUNT = 4;
    private HashCalculator hashCalculator = new HashCalculator();
    private String result;

    private CountDownLatch countDownLatch = new CountDownLatch(1);

    @Override
    public String crackPassword() throws InterruptedException, ExecutionException {
        setUpThreads();

        countDownLatch.await();
        return result;
    }


    private String generateNextPassword(String current, int incrementStep) {
        if (current.isEmpty()) {
            return "a";
        }

        final char lastChar = current.charAt(current.length() - 1);

        if (lastChar + incrementStep > 'z') {
            String prefix = current.substring(0, current.length() - 1);
            return generateNextPassword(prefix , 1) + incrementCharacter(lastChar, incrementStep);
        } else {
            char lastCharacter = current.charAt(current.length() - 1);
            lastCharacter = incrementCharacter(lastCharacter, incrementStep);
            return current.substring(0, current.length() - 1) + lastCharacter;
        }
    }

    private char incrementCharacter(char character, int step) {
        char newChar = (char) (character + step);
        if (newChar > 'z') {
            newChar = (char) (newChar - 'z' + 'a' - 1);
        }
        return newChar;
    }

    private void setUpThreads() {
        for (int i = 0; i < THREADS_COUNT; i++) {
            String firstPassword = "" + (char)('a' + i);
            final Executor executor = new Executor(firstPassword);
            new Thread(executor).start();
        }
    }

    private class Executor implements Runnable {

        private String password;

        public Executor(final String firstPassword) {
            this.password = firstPassword;
        }

        @Override
        public void run() {
            while (countDownLatch.getCount() > 0) {
                final String hash = hashCalculator.hash(password);
                if (hash.equals(DESIRED_HASH)) {
                    countDownLatch.countDown();
                    result = password;
                }
                password = generateNextPassword(password, THREADS_COUNT);
            }
        }
    }

}
