package com.epam.training;

import java.util.Date;
import java.util.concurrent.ExecutionException;

import com.epam.training.simple_solution.SimpleMultythreadedCracker;

public class App {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        PasswordCracker cracker = new SimpleMultythreadedCracker();
        final long before = new Date().getTime();
        System.out.println(cracker.crackPassword());
        final long after = new Date().getTime();
        System.out.println("Cracking time: " + (after - before) + " ms");

    }

}
