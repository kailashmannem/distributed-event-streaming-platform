package com.bitknight.common.hash.contract;

import com.bitknight.common.hash.PartitionStrategy;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public abstract class PartitionStrategyTest {
    protected abstract PartitionStrategy strategy();
    @RepeatedTest(50)
    void sameKeyShouldAlwaysMapToSamePartition() {
        PartitionStrategy strategy = strategy();
        int first = strategy.getPartition("user123", 5);
        for (int i = 0; i < 100; i++) {
            assertEquals(first, strategy.getPartition("user123", 5));
        }
    }

    @Test
    void partitionShouldAlwaysBeWithinRange() {
        PartitionStrategy strategy = strategy();
        for (int partitions = 1; partitions <= 20; partitions++) {
            for (int i = 0; i < 10000; i++) {
                int partition = strategy.getPartition("user" + i, partitions);
                assertTrue(partition >= 0);
                assertTrue(partition < partitions);
            }
        }
    }

    @Test
    void shouldHandleEmptyKey() {
        PartitionStrategy strategy = strategy();
        int partition = strategy.getPartition("", 10);
        assertTrue(partition >= 0);
        assertTrue(partition < 10);
    }

    @Test
    void shouldHandleLargePartitionCount() {
        PartitionStrategy strategy = strategy();
        int partition = strategy.getPartition("user123", 1000);
        assertTrue(partition >= 0);
        assertTrue(partition < 1000);
    }

    @Test
    void differentKeysShouldReturnValidPartitions() {
        PartitionStrategy strategy = strategy();
        String[] keys = {
                "user1",
                "user2",
                "user3",
                "order99",
                "transaction123"
        };
        for (String key : keys) {
            int partition = strategy.getPartition(key, 10);
            assertTrue(partition >= 0);
            assertTrue(partition < 10);
        }
    }
}