package com.epam.training;

import java.util.Date;
import java.util.concurrent.ExecutionException;

public class App {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        PasswordCrackerImpl cracker = new PasswordCrackerImpl();
        final long before = new Date().getTime();
        System.out.println(cracker.crackPassword());
        final long after = new Date().getTime();
        System.out.println("Cracking time: " + (after - before) + " ms");

    }

}
