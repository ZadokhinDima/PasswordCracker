package com.epam.training;

import java.util.concurrent.ExecutionException;

public class SingleThreadPasswordCrackerImpl implements PasswordCracker {

    private static final String DESIRED_HASH = "4fd0101ea3d0f5abbe296ef97f47afec";
    private HashCalculator hashCalculator = new HashCalculator();

    @Override
    public String crackPassword() throws InterruptedException, ExecutionException {
        String password = "";
        while (true) {
            final String hash = hashCalculator.hash(password);
            if (hash.equals(DESIRED_HASH)) {
                return password;
            }
            password = generateNextPassword(password);
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
}
