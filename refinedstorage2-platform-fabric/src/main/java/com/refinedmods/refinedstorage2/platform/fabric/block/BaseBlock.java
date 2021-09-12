package com.refinedmods.refinedstorage2.platform.fabric.block;

import com.refinedmods.refinedstorage2.platform.fabric.block.entity.BlockEntityWithDrops;
import com.refinedmods.refinedstorage2.platform.fabric.util.BiDirection;
import com.refinedmods.refinedstorage2.platform.fabric.util.WrenchUtil;

import java.util.Optional;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public abstract class BaseBlock extends Block {
    public static final EnumProperty<BiDirection> DIRECTION = EnumProperty.of("direction", BiDirection.class);

    protected BaseBlock(Settings settings) {
        super(settings);

        if (hasBiDirection()) {
            setDefaultState(getStateManager().getDefaultState().with(DIRECTION, BiDirection.NORTH));
        }
    }

    protected boolean hasBiDirection() {
        return false;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);

        if (hasBiDirection()) {
            builder.add(DIRECTION);
        }
    }

    private BiDirection getDirection(Direction playerFacing, float playerPitch) {
        if (playerPitch > 65) {
            return BiDirection.forUp(playerFacing);
        } else if (playerPitch < -65) {
            return BiDirection.forDown(playerFacing.getOpposite());
        } else {
            return BiDirection.forHorizontal(playerFacing.getOpposite());
        }
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState state = getDefaultState();

        if (hasBiDirection()) {
            state = state.with(DIRECTION, getDirection(ctx.getPlayerFacing(), ctx.getPlayer() != null ? ctx.getPlayer().getPitch() : 0));
        }

        return state;
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        if (!hasBiDirection()) {
            return state;
        }
        BiDirection currentDirection = state.get(DIRECTION);
        return state.with(DIRECTION, currentDirection.rotate());
    }

    @Override
    @SuppressWarnings("deprecation")
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        return tryRotate(state, world, pos, player, hand)
                .or(() -> tryOpenScreen(state, world, pos, player))
                .orElseGet(() -> super.onUse(state, world, pos, player, hand, hit));
    }

    private Optional<ActionResult> tryRotate(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand) {
        if (WrenchUtil.isWrench(player.getStackInHand(hand).getItem()) && WrenchUtil.isWrenchable(state)) {
            if (!world.isClient()) {
                world.setBlockState(pos, state.rotate(BlockRotation.CLOCKWISE_90));
                WrenchUtil.playWrenchSound(world, pos);
            }
            return Optional.of(ActionResult.CONSUME);
        }
        return Optional.empty();
    }

    private Optional<ActionResult> tryOpenScreen(BlockState state, World world, BlockPos pos, PlayerEntity player) {
        NamedScreenHandlerFactory screenHandlerFactory = state.createScreenHandlerFactory(world, pos);
        if (screenHandlerFactory != null) {
            if (!world.isClient()) {
                player.openHandledScreen(screenHandlerFactory);
            }
            return Optional.of(ActionResult.SUCCESS);
        }
        return Optional.empty();
    }

    @Override
    @SuppressWarnings("deprecation")
    public @Nullable NamedScreenHandlerFactory createScreenHandlerFactory(BlockState state, World world, BlockPos pos) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        return blockEntity instanceof NamedScreenHandlerFactory factory ? factory : null;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock() && !state.getBlock().getClass().equals(newState.getBlock().getClass())) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof BlockEntityWithDrops drops) {
                ItemScatterer.spawn(world, pos, drops.getDrops());
                world.updateComparators(pos, this);
            }

            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }
}
