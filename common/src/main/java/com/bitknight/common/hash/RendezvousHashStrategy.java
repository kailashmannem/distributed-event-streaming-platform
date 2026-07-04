package com.bitknight.common.hash;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class RendezvousHashStrategy implements PartitionStrategy{
    private final MessageDigest messageDigest;
    RendezvousHashStrategy(MessageDigest messageDigest) {
        try {
            this.messageDigest = MessageDigest.getInstance("SHA-256");
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public int getPartition(String key, int partitionCount) {
        int bestPartition = -1;
        long bestScore = Long.MIN_VALUE;
        for(int partition = 0; partition < partitionCount; partition++){
            long score = calculateScore(key, partition);
            if (score > bestScore) {
                bestPartition = partition;
                bestScore = score;
            }
        }
        return bestPartition;
    }
    private long calculateScore(String key, int partition) {
        String input = key + "-" + partition;
        byte[] digest = messageDigest.digest(input.getBytes(StandardCharsets.UTF_8));
        return ByteBuffer.wrap(digest).getLong();
    }
}
