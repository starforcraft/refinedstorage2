package com.refinedmods.refinedstorage2.platform.common;

import com.refinedmods.refinedstorage2.platform.api.PlatformApi;
import com.refinedmods.refinedstorage2.platform.api.resource.FluidResource;
import com.refinedmods.refinedstorage2.platform.api.resource.ItemResource;
import com.refinedmods.refinedstorage2.platform.common.containermenu.grid.GridContainerMenu;
import com.refinedmods.refinedstorage2.platform.common.containermenu.grid.WirelessGridContainerMenu;
import com.refinedmods.refinedstorage2.platform.common.content.Menus;
import com.refinedmods.refinedstorage2.platform.common.internal.resource.FluidResourceRendering;
import com.refinedmods.refinedstorage2.platform.common.internal.resource.ItemResourceRendering;
import com.refinedmods.refinedstorage2.platform.common.screen.ConstructorScreen;
import com.refinedmods.refinedstorage2.platform.common.screen.ControllerScreen;
import com.refinedmods.refinedstorage2.platform.common.screen.DestructorScreen;
import com.refinedmods.refinedstorage2.platform.common.screen.DiskDriveScreen;
import com.refinedmods.refinedstorage2.platform.common.screen.ExporterScreen;
import com.refinedmods.refinedstorage2.platform.common.screen.ExternalStorageScreen;
import com.refinedmods.refinedstorage2.platform.common.screen.FluidStorageBlockScreen;
import com.refinedmods.refinedstorage2.platform.common.screen.ImporterScreen;
import com.refinedmods.refinedstorage2.platform.common.screen.InterfaceScreen;
import com.refinedmods.refinedstorage2.platform.common.screen.ItemStorageBlockScreen;
import com.refinedmods.refinedstorage2.platform.common.screen.RegulatorUpgradeScreen;
import com.refinedmods.refinedstorage2.platform.common.screen.WirelessTransmitterScreen;
import com.refinedmods.refinedstorage2.platform.common.screen.amount.DetectorScreen;
import com.refinedmods.refinedstorage2.platform.common.screen.grid.CraftingGridScreen;
import com.refinedmods.refinedstorage2.platform.common.screen.grid.GridScreen;
import com.refinedmods.refinedstorage2.platform.common.screen.grid.hint.FluidGridInsertionHint;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

public abstract class AbstractClientModInitializer {
    protected static void registerScreens(final ScreenRegistration registration) {
        registration.register(Menus.INSTANCE.getDiskDrive(), DiskDriveScreen::new);
        registration.register(Menus.INSTANCE.getGrid(), GridScreen<GridContainerMenu>::new);
        registration.register(Menus.INSTANCE.getCraftingGrid(), CraftingGridScreen::new);
        registration.register(Menus.INSTANCE.getWirelessGrid(), GridScreen<WirelessGridContainerMenu>::new);
        registration.register(Menus.INSTANCE.getController(), ControllerScreen::new);
        registration.register(Menus.INSTANCE.getItemStorage(), ItemStorageBlockScreen::new);
        registration.register(Menus.INSTANCE.getFluidStorage(), FluidStorageBlockScreen::new);
        registration.register(Menus.INSTANCE.getImporter(), ImporterScreen::new);
        registration.register(Menus.INSTANCE.getExporter(), ExporterScreen::new);
        registration.register(Menus.INSTANCE.getInterface(), InterfaceScreen::new);
        registration.register(Menus.INSTANCE.getExternalStorage(), ExternalStorageScreen::new);
        registration.register(Menus.INSTANCE.getDetector(), DetectorScreen::new);
        registration.register(Menus.INSTANCE.getDestructor(), DestructorScreen::new);
        registration.register(Menus.INSTANCE.getConstructor(), ConstructorScreen::new);
        registration.register(Menus.INSTANCE.getRegulatorUpgrade(), RegulatorUpgradeScreen::new);
        registration.register(Menus.INSTANCE.getWirelessTransmitter(), WirelessTransmitterScreen::new);
    }

    protected static void registerAlternativeGridHints() {
        PlatformApi.INSTANCE.addAlternativeGridInsertionHint(new FluidGridInsertionHint());
    }

    protected static void registerResourceRendering() {
        PlatformApi.INSTANCE.registerResourceRendering(ItemResource.class, new ItemResourceRendering());
        PlatformApi.INSTANCE.registerResourceRendering(FluidResource.class, new FluidResourceRendering());
    }

    @FunctionalInterface
    public interface ScreenRegistration {
        <M extends AbstractContainerMenu, U extends Screen & MenuAccess<M>> void register(MenuType<? extends M> type,
                                                                                          ScreenConstructor<M, U>
                                                                                              factory);
    }

    @FunctionalInterface
    public interface ScreenConstructor<T extends AbstractContainerMenu, U extends Screen & MenuAccess<T>> {
        U create(T menu, Inventory inventory, Component title);
    }
}
