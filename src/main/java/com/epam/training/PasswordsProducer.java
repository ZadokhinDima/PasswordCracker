package com.epam.training;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class PasswordsProducer implements Runnable {

    private BlockingQueue<String> passwordsToCheck;

    private static BlockingQueue<String> queueForPasswordGeneration = new LinkedBlockingQueue<>(200);

    private String LETTERS = "abcdefghijklmnopqrstuvwxyz";

    public PasswordsProducer(final BlockingQueue<String> passwordsToCheck) {
        this.passwordsToCheck = passwordsToCheck;
    }

    @Override
    public void run() {
        generateNextLetterCombinations("");
        while (true) {
            generateNextLetterCombinations(queueForPasswordGeneration.poll());
        }
    }

    private void generateNextLetterCombinations(String password) {
        LETTERS.chars().mapToObj(i -> (char)i).forEach(letter -> putToTaskQueue(password + letter));
    }

    private void putToTaskQueue(String password) {
        try {
            passwordsToCheck.put(password);
            queueForPasswordGeneration.put(password);
        } catch (InterruptedException e) {
            System.out.println("Stopped producer thread");
        }
    }
}
