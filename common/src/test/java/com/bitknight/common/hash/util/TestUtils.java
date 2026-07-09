package com.bitknight.common.hash.util;

import java.util.ArrayList;
import java.util.List;

public final class TestUtils {
    private TestUtils() {
    }

    public static List<String> generateKeys(int count) {
        List<String> keys = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            keys.add("user" + i);
        }
        return keys;
    }

    public static void printDistribution(int[] distribution) {
        System.out.println();
        for (int i = 0; i < distribution.length; i++) {
            System.out.printf(
                    "Partition %d -> %d%n",
                    i,
                    distribution[i]
            );
        }
        System.out.println();
    }

    public static double expectedAverage(int totalKeys, int partitions) {
        return (double) totalKeys / partitions;
    }
}