package com.bitknight.common.hash.simulation;

import com.bitknight.common.hash.ModuloHashStrategy;
import com.bitknight.common.hash.PartitionStrategy;
import com.bitknight.common.hash.RendezvousHashStrategy;
import org.junit.jupiter.api.Test;

public class PerformanceTest {

    @Test
    void benchmarkStrategies() {
        benchmark(new ModuloHashStrategy(), "Modulo");
        benchmark(new RendezvousHashStrategy(), "Rendezvous");
    }

    private void benchmark(PartitionStrategy strategy, String name) {
        long start = System.nanoTime();
        for (int i = 0; i < 1_000_000; i++) {
            strategy.getPartition("user" + i, 20);
        }
        long end = System.nanoTime();
        double millis = (end - start) / 1_000_000.0;
        System.out.printf("%s : %.2f ms%n", name, millis);
    }
}