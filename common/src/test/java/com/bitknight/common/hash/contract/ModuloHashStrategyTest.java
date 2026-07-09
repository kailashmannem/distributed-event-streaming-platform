package com.bitknight.common.hash.contract;

import com.bitknight.common.hash.ModuloHashStrategy;
import com.bitknight.common.hash.PartitionStrategy;

public class ModuloHashStrategyTest extends PartitionStrategyTest {

    @Override
    protected PartitionStrategy strategy() {
        return new ModuloHashStrategy();
    }
}