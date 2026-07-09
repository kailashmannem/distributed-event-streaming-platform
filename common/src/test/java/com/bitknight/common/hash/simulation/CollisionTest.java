package com.bitknight.common.hash.simulation;

import com.bitknight.common.hash.PartitionStrategy;
import com.bitknight.common.hash.RendezvousHashStrategy;
import com.bitknight.common.hash.util.TestUtils;
import org.junit.jupiter.api.Test;

import java.util.List;

public class CollisionTest {

    @Test
    void collisionAnalysis() {

        PartitionStrategy strategy =
                new RendezvousHashStrategy();

        int partitions = 10;

        int[] buckets =
                new int[partitions];

        List<String> keys =
                TestUtils.generateKeys(100000);

        for (String key : keys) {

            int partition =
                    strategy.getPartition(
                            key,
                            partitions
                    );

            buckets[partition]++;
        }

        System.out.println();

        for (int i = 0; i < partitions; i++) {

            System.out.printf(
                    "Partition %d -> %d keys%n",
                    i,
                    buckets[i]
            );
        }
    }
}