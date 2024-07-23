package com.refinedmods.refinedstorage.common.upgrade;

import com.refinedmods.refinedstorage.common.Platform;
import com.refinedmods.refinedstorage.common.api.RefinedStorageApi;
import com.refinedmods.refinedstorage.common.api.support.HelpTooltipComponent;
import com.refinedmods.refinedstorage.common.api.upgrade.AbstractUpgradeItem;
import com.refinedmods.refinedstorage.common.api.upgrade.UpgradeRegistry;

import java.util.Optional;
import java.util.function.LongSupplier;

import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import static com.refinedmods.refinedstorage.common.util.IdentifierUtil.createTranslation;

public final class SimpleUpgradeItem extends AbstractUpgradeItem {
    private final LongSupplier energyUsageResolver;
    private final boolean foil;
    private final Component helpText;

    private SimpleUpgradeItem(final UpgradeRegistry registry,
                              final LongSupplier energyUsageResolver,
                              final boolean foil,
                              final Component helpText) {
        super(new Item.Properties(), registry);
        this.energyUsageResolver = energyUsageResolver;
        this.foil = foil;
        this.helpText = helpText;
    }

    @Override
    public long getEnergyUsage() {
        return energyUsageResolver.getAsLong();
    }

    @Override
    public boolean isFoil(final ItemStack stack) {
        return foil;
    }

    @Override
    public Optional<TooltipComponent> getTooltipImage(final ItemStack stack) {
        return Optional.of(new HelpTooltipComponent(helpText));
    }

    public static SimpleUpgradeItem speedUpgrade() {
        return new SimpleUpgradeItem(
            RefinedStorageApi.INSTANCE.getUpgradeRegistry(),
            Platform.INSTANCE.getConfig().getUpgrade()::getSpeedUpgradeEnergyUsage,
            false,
            createTranslation("item", "speed_upgrade.help")
        );
    }

    public static SimpleUpgradeItem stackUpgrade() {
        return new SimpleUpgradeItem(
            RefinedStorageApi.INSTANCE.getUpgradeRegistry(),
            Platform.INSTANCE.getConfig().getUpgrade()::getStackUpgradeEnergyUsage,
            false,
            createTranslation("item", "stack_upgrade.help")
        );
    }

    public static SimpleUpgradeItem silkTouchUpgrade() {
        return new SimpleUpgradeItem(
            RefinedStorageApi.INSTANCE.getUpgradeRegistry(),
            Platform.INSTANCE.getConfig().getUpgrade()::getSilkTouchUpgradeEnergyUsage,
            true,
            createTranslation("item", "silk_touch_upgrade.help")
        );
    }
}
