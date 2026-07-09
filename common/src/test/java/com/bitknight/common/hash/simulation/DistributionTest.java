package com.bitknight.common.hash.simulation;

import com.bitknight.common.hash.PartitionStrategy;
import com.bitknight.common.hash.RendezvousHashStrategy;
import com.bitknight.common.hash.util.TestUtils;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class DistributionTest {

    private static final int KEY_COUNT = 100_000;
    private static final int PARTITIONS = 10;

    @Test
    void shouldDistributeKeysEvenly() {
        PartitionStrategy strategy = new RendezvousHashStrategy();
        List<String> keys = TestUtils.generateKeys(KEY_COUNT);
        int[] distribution = new int[PARTITIONS];
        for (String key : keys) {
            int partition = strategy.getPartition(key, PARTITIONS);
            distribution[partition]++;
        }
        TestUtils.printDistribution(distribution);
        double expected = TestUtils.expectedAverage(KEY_COUNT, PARTITIONS);
        double tolerance = expected * 0.10;
        for (int count : distribution) {
            assertTrue(
                    Math.abs(count - expected)
                            <= tolerance,
                    "Distribution is too uneven."
            );
        }
    }
}