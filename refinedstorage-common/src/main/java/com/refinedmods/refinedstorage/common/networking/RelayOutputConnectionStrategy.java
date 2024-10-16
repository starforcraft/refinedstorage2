package com.refinedmods.refinedstorage.common.networking;

import com.refinedmods.refinedstorage.common.api.support.network.ConnectionSink;
import com.refinedmods.refinedstorage.common.api.support.network.ConnectionStrategy;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;

class RelayOutputConnectionStrategy implements ConnectionStrategy {
    private final RelayBlockEntity blockEntity;

    RelayOutputConnectionStrategy(final RelayBlockEntity blockEntity) {
        this.blockEntity = blockEntity;
    }

    // The output network node container must always have an outgoing and incoming connection.
    // If not, network node containers after the output network node container may end up without a network
    // because the graph algorithm won't be able to work properly and won't be able to reassign a new network.
    // The output network node container *never* provides a connection with the input network node container.
    // The input network node container controls whether the output network node container is connected.
    @Override
    public void addOutgoingConnections(final ConnectionSink sink) {
        final Direction direction = blockEntity.getDirectionInternal();
        sink.tryConnectInSameDimension(
            blockEntity.getBlockPos().relative(direction),
            direction.getOpposite()
        );
    }

    @Override
    public boolean canAcceptIncomingConnection(final Direction incomingDirection, final BlockState connectingState) {
        return incomingDirection == blockEntity.getDirectionInternal();
    }
}
