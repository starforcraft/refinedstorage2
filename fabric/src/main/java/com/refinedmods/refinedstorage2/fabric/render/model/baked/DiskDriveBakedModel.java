package com.refinedmods.refinedstorage2.fabric.render.model.baked;

import com.refinedmods.refinedstorage2.core.storage.disk.DiskState;
import com.refinedmods.refinedstorage2.fabric.block.DiskDriveBlock;
import net.fabricmc.fabric.api.renderer.v1.model.ForwardingBakedModel;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.fabricmc.fabric.api.rendering.data.v1.RenderAttachedBlockView;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;

import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

public class DiskDriveBakedModel extends ForwardingBakedModel {
    private final BakedModel diskModel;
    private final QuadTranslator[] translators = new QuadTranslator[8];

    public DiskDriveBakedModel(BakedModel baseModel, BakedModel diskModel) {
        this.wrapped = baseModel;
        this.diskModel = diskModel;

        int i = 0;
        for (int y = 0; y < 4; ++y) {
            for (int x = 0; x < 2; ++x) {
                translators[i++] = new QuadTranslator(x == 0 ? -(2F / 16F) : -(9F / 16F), -((y * 3F) / 16F) - (2F / 16F), 0);
            }
        }
    }

    @Override
    public void emitBlockQuads(BlockRenderView blockView, BlockState state, BlockPos pos, Supplier<Random> randomSupplier, RenderContext context) {
        QuadRotator rotator = new QuadRotator(state.get(DiskDriveBlock.DIRECTION));

        context.pushTransform(rotator);

        super.emitBlockQuads(blockView, state, pos, randomSupplier, context);

        if (blockView instanceof RenderAttachedBlockView) {
            Object diskStates = ((RenderAttachedBlockView) blockView).getBlockEntityRenderAttachment(pos);
            if (diskStates != null) {
                for (int i = 0; i < translators.length; ++i) {
                    if (((List<DiskState>) diskStates).get(i) != DiskState.NONE) {
                        context.pushTransform(translators[i]);
                        context.fallbackConsumer().accept(diskModel);
                        context.popTransform();
                    }
                }
            }
        }

        context.popTransform();
    }
}
