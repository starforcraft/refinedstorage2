package com.refinedmods.refinedstorage2.platform.common.block.entity.grid;

import com.refinedmods.refinedstorage2.platform.api.PlatformApi;
import com.refinedmods.refinedstorage2.platform.api.resource.FluidResource;
import com.refinedmods.refinedstorage2.platform.common.containermenu.grid.FluidGridContainerMenu;
import com.refinedmods.refinedstorage2.platform.common.content.BlockEntities;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;

import static com.refinedmods.refinedstorage2.platform.common.util.IdentifierUtil.createTranslation;

public class FluidGridBlockEntity extends AbstractGridBlockEntity<FluidResource> {
    public FluidGridBlockEntity(final BlockPos pos, final BlockState state) {
        super(BlockEntities.INSTANCE.getFluidGrid(), pos, state, PlatformApi.INSTANCE.getStorageChannelTypeRegistry());
    }

    @Override
    public Component getDisplayName() {
        return createTranslation("block", "fluid_grid");
    }

    @Override
    public AbstractContainerMenu createMenu(final int syncId, final Inventory inv, final Player player) {
        return new FluidGridContainerMenu(syncId, inv, this);
    }
}
