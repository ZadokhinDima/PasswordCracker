package com.epam.training.producer_consumer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingQueue;

import com.epam.training.PasswordCracker;

public class ProducerConsumerPasswordCracker implements PasswordCracker {

    private static final Integer PRODUCER_COUNT = 1;
    private static final Integer CONSUMER_COUNT = 3;

    private CountDownLatch countDownLatch = new CountDownLatch(1);

    private BlockingQueue<String> passwordsToCheck = new LinkedBlockingQueue<>(200);

    private List<Thread> threads;

    public String crackPassword() throws InterruptedException, ExecutionException {
        setUpThreads();
        countDownLatch.await();
        return "";
    }

    private void setUpThreads() {
        PasswordsProducer producer = new PasswordsProducer(passwordsToCheck, countDownLatch);
        PasswordsConsumer consumer = new PasswordsConsumer(passwordsToCheck, countDownLatch);
        threads = new ArrayList<>();

        for (int i = 0; i < PRODUCER_COUNT; i++) {
            threads.add(new Thread(producer));
        }
        for (int i = 0; i < CONSUMER_COUNT; i++) {
            threads.add(new Thread(consumer));
        }

        threads.forEach(Thread::start);

    }


}
