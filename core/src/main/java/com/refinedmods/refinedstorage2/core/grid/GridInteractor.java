package com.refinedmods.refinedstorage2.core.grid;

import com.refinedmods.refinedstorage2.core.storage.Source;
import com.refinedmods.refinedstorage2.core.util.Action;
import net.minecraft.item.ItemStack;

public interface GridInteractor extends Source {
    ItemStack getCursorStack();

    void setCursorStack(ItemStack stack);

    ItemStack insertIntoInventory(ItemStack stack, int preferredSlot, Action action);

    ItemStack extractFromInventory(ItemStack template, int slot, int count, Action action);
}
