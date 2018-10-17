package com.epam.training;

import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class PasswordCrackerImpl implements PasswordCracker {

    private CountDownLatch countDownLatch = new CountDownLatch(1);

    private BlockingQueue<String> passwordsToCheck = new LinkedBlockingQueue<>(20);

    public String crackPassword() throws InterruptedException, ExecutionException {
        PasswordsProducer producer = new PasswordsProducer(passwordsToCheck);
        PasswordsConsumer consumer = new PasswordsConsumer(passwordsToCheck, countDownLatch);
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        executorService.submit(producer);
        executorService.submit(producer);
        final Future<String> firstFuture = executorService.submit(consumer);
        final Future<String> secondFuture = executorService.submit(consumer);


        countDownLatch.await();
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.SECONDS);
        final Optional<Future<String>> finishedFuture =
                Stream.of(firstFuture, secondFuture).filter(Future::isDone).findFirst();
        return finishedFuture.get().get();
    }

}
