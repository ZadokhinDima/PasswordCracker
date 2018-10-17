package com.epam.training;

import java.util.concurrent.ExecutionException;

public interface PasswordCracker {

    String LETTERS = "abcdefghijklmnopqrstuvwxyz";

    String crackPassword() throws InterruptedException, ExecutionException;

}
