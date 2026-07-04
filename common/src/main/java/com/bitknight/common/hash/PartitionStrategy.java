package com.bitknight.common.hash;

public interface PartitionStrategy {
    int getPartition(String key, int partitionCount);
}
