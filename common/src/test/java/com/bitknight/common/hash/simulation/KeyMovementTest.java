package com.bitknight.common.hash.simulation;

import com.bitknight.common.hash.ModuloHashStrategy;
import com.bitknight.common.hash.PartitionStrategy;
import com.bitknight.common.hash.RendezvousHashStrategy;
import com.bitknight.common.hash.util.TestUtils;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KeyMovementTest {

    private static final int KEY_COUNT = 100_000;

    @Test
    void compareKeyMovement() {
        compare(new ModuloHashStrategy(), "Modulo");
        compare(new RendezvousHashStrategy(), "Rendezvous");
    }

    private void compare(PartitionStrategy strategy, String strategyName) {
        List<String> keys = TestUtils.generateKeys(KEY_COUNT);
        Map<String, Integer> before = new HashMap<>();
        Map<String, Integer> after = new HashMap<>();
        for (String key : keys) {
            before.put(key, strategy.getPartition(key, 3));
        }
        for (String key : keys) {
            after.put(key, strategy.getPartition(key, 6));
        }
        int moved = 0;
        for (String key : keys) {
            if (!before.get(key).equals(after.get(key))) {
                moved++;
            }
        }
        double percentage = (moved * 100.0) / KEY_COUNT;

        System.out.println();
        System.out.println("===============================");
        System.out.println(strategyName);
        System.out.println("===============================");
        System.out.println("Keys Tested : " + KEY_COUNT);
        System.out.println("Moved Keys  : " + moved);
        System.out.printf("Movement %% : %.2f%n",
                percentage);
    }
}