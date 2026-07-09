package com.bitknight.common.hash.contract;

import com.bitknight.common.hash.PartitionStrategy;
import com.bitknight.common.hash.RendezvousHashStrategy;

public class RendezvousHashStrategyTest extends PartitionStrategyTest {

    @Override
    protected PartitionStrategy strategy() {
        return new RendezvousHashStrategy();
    }
}