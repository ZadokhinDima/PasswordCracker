package com.epam.training;

import java.util.Date;

public class App {

    public static void main(String[] args) {

        PasswordCrackerImpl cracker = new PasswordCrackerImpl("4fd0101ea3d0f5abbe296ef97f47afec");
        final long before = new Date().getTime();
        System.out.println(cracker.crackPassword());
        final long after = new Date().getTime();
        System.out.println("Cracking time: " + (after - before) + " ms");

    }

}
