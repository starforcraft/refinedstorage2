package com.refinedmods.refinedstorage2.core.network.node;

import com.refinedmods.refinedstorage2.core.util.Position;

public class EmptyNetworkNode extends NetworkNodeImpl {
    public EmptyNetworkNode(Position position) {
        super(position);
    }

    @Override
    protected long getEnergyUsage() {
        return 0;
    }
}
