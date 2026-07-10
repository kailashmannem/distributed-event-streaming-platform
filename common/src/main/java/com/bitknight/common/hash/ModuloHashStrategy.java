package com.bitknight.common.hash;

public class ModuloHashStrategy implements PartitionStrategy {
    @Override
    public int getPartition(String key, int partitionCount) {
        return Math.floorMod(key.hashCode(), partitionCount);
    }
}
