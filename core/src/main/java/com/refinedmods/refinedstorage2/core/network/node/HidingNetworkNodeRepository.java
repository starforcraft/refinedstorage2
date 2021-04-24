package com.refinedmods.refinedstorage2.core.network.node;

import java.util.Optional;

import com.refinedmods.refinedstorage2.core.util.Position;

public class HidingNetworkNodeRepository implements NetworkNodeRepository {
    private final NetworkNodeRepository parent;
    private final Position hiddenPos;

    public HidingNetworkNodeRepository(NetworkNodeRepository parent, Position hiddenPos) {
        this.parent = parent;
        this.hiddenPos = hiddenPos;
    }

    @Override
    public Optional<NetworkNode> getNode(Position pos) {
        if (hiddenPos.equals(pos)) {
            return Optional.empty();
        }

        return parent.getNode(pos);
    }
}
