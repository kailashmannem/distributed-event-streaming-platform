package com.bitknight.common.hash.simulation;

import com.bitknight.common.hash.PartitionStrategy;
import com.bitknight.common.hash.RendezvousHashStrategy;
import com.bitknight.common.hash.util.TestUtils;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScalingTest {

    private final PartitionStrategy strategy = new RendezvousHashStrategy();

    @Test
    void scalingAnalysis() {
        int[] partitionCounts = {3, 4, 5, 6, 8, 10, 20};
        List<String> keys = TestUtils.generateKeys(100000);
        for (int i = 0; i < partitionCounts.length - 1; i++) {
            int oldCount = partitionCounts[i];
            int newCount = partitionCounts[i + 1];
            Map<String, Integer> before = new HashMap<>();
            Map<String, Integer> after = new HashMap<>();
            for (String key : keys) {
                before.put(key, strategy.getPartition(key, oldCount));
                after.put(key, strategy.getPartition(key, newCount));
            }
            int moved = 0;
            for (String key : keys) {
                if (!before.get(key).equals(after.get(key))) moved++;
            }
            System.out.printf("%d -> %d : %.2f%%%n", oldCount, newCount, moved * 100.0 / keys.size());
        }
    }
}
