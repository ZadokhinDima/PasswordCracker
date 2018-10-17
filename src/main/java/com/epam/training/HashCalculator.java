package com.epam.training;

import java.nio.charset.StandardCharsets;

import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;

public class HashCalculator {

    public String hash(String toHash) {
        Hasher hasher = Hashing.md5().newHasher();
        hasher.putString(toHash, StandardCharsets.UTF_8);
        return hasher.hash().toString();
    }

}
