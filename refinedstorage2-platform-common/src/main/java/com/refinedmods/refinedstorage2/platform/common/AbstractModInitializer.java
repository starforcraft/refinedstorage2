package com.refinedmods.refinedstorage2.platform.common;

import com.refinedmods.refinedstorage2.api.network.component.EnergyNetworkComponent;
import com.refinedmods.refinedstorage2.api.network.component.StorageNetworkComponent;
import com.refinedmods.refinedstorage2.api.network.impl.component.EnergyNetworkComponentImpl;
import com.refinedmods.refinedstorage2.api.network.impl.component.GraphNetworkComponent;
import com.refinedmods.refinedstorage2.api.network.impl.component.StorageNetworkComponentImpl;
import com.refinedmods.refinedstorage2.platform.api.PlatformApi;
import com.refinedmods.refinedstorage2.platform.api.PlatformApiProxy;
import com.refinedmods.refinedstorage2.platform.common.block.ControllerType;
import com.refinedmods.refinedstorage2.platform.common.block.DiskDriveBlock;
import com.refinedmods.refinedstorage2.platform.common.block.FluidStorageBlock;
import com.refinedmods.refinedstorage2.platform.common.block.InterfaceBlock;
import com.refinedmods.refinedstorage2.platform.common.block.ItemStorageBlock;
import com.refinedmods.refinedstorage2.platform.common.block.SimpleBlock;
import com.refinedmods.refinedstorage2.platform.common.block.StorageMonitorBlock;
import com.refinedmods.refinedstorage2.platform.common.block.entity.CableBlockEntity;
import com.refinedmods.refinedstorage2.platform.common.block.entity.ControllerBlockEntity;
import com.refinedmods.refinedstorage2.platform.common.block.entity.ImporterBlockEntity;
import com.refinedmods.refinedstorage2.platform.common.block.entity.constructor.ConstructorBlockEntity;
import com.refinedmods.refinedstorage2.platform.common.block.entity.constructor.ItemDropConstructorStrategyFactory;
import com.refinedmods.refinedstorage2.platform.common.block.entity.constructor.PlaceBlockConstructorStrategy;
import com.refinedmods.refinedstorage2.platform.common.block.entity.constructor.PlaceFireworksConstructorStrategy;
import com.refinedmods.refinedstorage2.platform.common.block.entity.constructor.PlaceFluidConstructorStrategy;
import com.refinedmods.refinedstorage2.platform.common.block.entity.destructor.BlockBreakDestructorStrategyFactory;
import com.refinedmods.refinedstorage2.platform.common.block.entity.destructor.DestructorBlockEntity;
import com.refinedmods.refinedstorage2.platform.common.block.entity.destructor.FluidBreakDestructorStrategyFactory;
import com.refinedmods.refinedstorage2.platform.common.block.entity.destructor.ItemPickupDestructorStrategyFactory;
import com.refinedmods.refinedstorage2.platform.common.block.entity.detector.DetectorBlockEntity;
import com.refinedmods.refinedstorage2.platform.common.block.entity.diskdrive.AbstractDiskDriveBlockEntity;
import com.refinedmods.refinedstorage2.platform.common.block.entity.exporter.ExporterBlockEntity;
import com.refinedmods.refinedstorage2.platform.common.block.entity.externalstorage.ExternalStorageBlockEntity;
import com.refinedmods.refinedstorage2.platform.common.block.entity.grid.CraftingGridBlockEntity;
import com.refinedmods.refinedstorage2.platform.common.block.entity.grid.GridBlockEntity;
import com.refinedmods.refinedstorage2.platform.common.block.entity.iface.InterfaceBlockEntity;
import com.refinedmods.refinedstorage2.platform.common.block.entity.storage.FluidStorageBlockBlockEntity;
import com.refinedmods.refinedstorage2.platform.common.block.entity.storage.ItemStorageBlockBlockEntity;
import com.refinedmods.refinedstorage2.platform.common.block.entity.storagemonitor.FluidStorageMonitorExtractionStrategy;
import com.refinedmods.refinedstorage2.platform.common.block.entity.storagemonitor.FluidStorageMonitorInsertionStrategy;
import com.refinedmods.refinedstorage2.platform.common.block.entity.storagemonitor.ItemStorageMonitorExtractionStrategy;
import com.refinedmods.refinedstorage2.platform.common.block.entity.storagemonitor.ItemStorageMonitorInsertionStrategy;
import com.refinedmods.refinedstorage2.platform.common.block.entity.storagemonitor.StorageMonitorBlockEntity;
import com.refinedmods.refinedstorage2.platform.common.block.entity.wirelesstransmitter.BaseWirelessTransmitterRangeModifier;
import com.refinedmods.refinedstorage2.platform.common.block.entity.wirelesstransmitter.CreativeRangeUpgradeWirelessTransmitterRangeModifier;
import com.refinedmods.refinedstorage2.platform.common.block.entity.wirelesstransmitter.RangeUpgradeWirelessTransmitterRangeModifier;
import com.refinedmods.refinedstorage2.platform.common.block.entity.wirelesstransmitter.WirelessTransmitterBlockEntity;
import com.refinedmods.refinedstorage2.platform.common.containermenu.ConstructorContainerMenu;
import com.refinedmods.refinedstorage2.platform.common.containermenu.ControllerContainerMenu;
import com.refinedmods.refinedstorage2.platform.common.containermenu.DestructorContainerMenu;
import com.refinedmods.refinedstorage2.platform.common.containermenu.ExporterContainerMenu;
import com.refinedmods.refinedstorage2.platform.common.containermenu.ImporterContainerMenu;
import com.refinedmods.refinedstorage2.platform.common.containermenu.InterfaceContainerMenu;
import com.refinedmods.refinedstorage2.platform.common.containermenu.RegulatorUpgradeContainerMenu;
import com.refinedmods.refinedstorage2.platform.common.containermenu.StorageMonitorContainerMenu;
import com.refinedmods.refinedstorage2.platform.common.containermenu.WirelessTransmitterContainerMenu;
import com.refinedmods.refinedstorage2.platform.common.containermenu.detector.DetectorContainerMenu;
import com.refinedmods.refinedstorage2.platform.common.containermenu.grid.CraftingGridContainerMenu;
import com.refinedmods.refinedstorage2.platform.common.containermenu.grid.GridContainerMenu;
import com.refinedmods.refinedstorage2.platform.common.containermenu.grid.WirelessGridContainerMenu;
import com.refinedmods.refinedstorage2.platform.common.containermenu.storage.ExternalStorageContainerMenu;
import com.refinedmods.refinedstorage2.platform.common.containermenu.storage.block.FluidStorageBlockContainerMenu;
import com.refinedmods.refinedstorage2.platform.common.containermenu.storage.block.ItemStorageBlockContainerMenu;
import com.refinedmods.refinedstorage2.platform.common.containermenu.storage.diskdrive.DiskDriveContainerMenu;
import com.refinedmods.refinedstorage2.platform.common.content.BlockEntities;
import com.refinedmods.refinedstorage2.platform.common.content.BlockEntityTypeFactory;
import com.refinedmods.refinedstorage2.platform.common.content.Blocks;
import com.refinedmods.refinedstorage2.platform.common.content.ContentIds;
import com.refinedmods.refinedstorage2.platform.common.content.Items;
import com.refinedmods.refinedstorage2.platform.common.content.LootFunctions;
import com.refinedmods.refinedstorage2.platform.common.content.MenuTypeFactory;
import com.refinedmods.refinedstorage2.platform.common.content.Menus;
import com.refinedmods.refinedstorage2.platform.common.content.RegistryCallback;
import com.refinedmods.refinedstorage2.platform.common.content.Sounds;
import com.refinedmods.refinedstorage2.platform.common.internal.resource.FluidResourceFactory;
import com.refinedmods.refinedstorage2.platform.common.internal.storage.channel.StorageChannelTypes;
import com.refinedmods.refinedstorage2.platform.common.internal.storage.type.FluidStorageType;
import com.refinedmods.refinedstorage2.platform.common.internal.storage.type.ItemStorageType;
import com.refinedmods.refinedstorage2.platform.common.internal.storage.type.StorageTypes;
import com.refinedmods.refinedstorage2.platform.common.internal.upgrade.UpgradeDestinations;
import com.refinedmods.refinedstorage2.platform.common.item.ConfigurationCardItem;
import com.refinedmods.refinedstorage2.platform.common.item.FluidStorageDiskItem;
import com.refinedmods.refinedstorage2.platform.common.item.FortuneUpgradeItem;
import com.refinedmods.refinedstorage2.platform.common.item.ItemStorageDiskItem;
import com.refinedmods.refinedstorage2.platform.common.item.ProcessorItem;
import com.refinedmods.refinedstorage2.platform.common.item.RangeUpgradeItem;
import com.refinedmods.refinedstorage2.platform.common.item.RegulatorUpgradeItem;
import com.refinedmods.refinedstorage2.platform.common.item.SimpleItem;
import com.refinedmods.refinedstorage2.platform.common.item.SimpleUpgradeItem;
import com.refinedmods.refinedstorage2.platform.common.item.WirelessGridItem;
import com.refinedmods.refinedstorage2.platform.common.item.WrenchItem;
import com.refinedmods.refinedstorage2.platform.common.item.block.FluidStorageBlockBlockItem;
import com.refinedmods.refinedstorage2.platform.common.item.block.ItemStorageBlockBlockItem;
import com.refinedmods.refinedstorage2.platform.common.item.block.SimpleBlockItem;
import com.refinedmods.refinedstorage2.platform.common.loot.EnergyLootItemFunctionSerializer;
import com.refinedmods.refinedstorage2.platform.common.loot.StorageBlockLootItemFunctionSerializer;
import com.refinedmods.refinedstorage2.platform.common.recipe.UpgradeWithEnchantedBookRecipeSerializer;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Supplier;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;

import static com.refinedmods.refinedstorage2.platform.common.content.ContentIds.CABLE;
import static com.refinedmods.refinedstorage2.platform.common.content.ContentIds.CONSTRUCTION_CORE;
import static com.refinedmods.refinedstorage2.platform.common.content.ContentIds.CONSTRUCTOR;
import static com.refinedmods.refinedstorage2.platform.common.content.ContentIds.CONTROLLER;
import static com.refinedmods.refinedstorage2.platform.common.content.ContentIds.CRAFTING_GRID;
import static com.refinedmods.refinedstorage2.platform.common.content.ContentIds.CREATIVE_CONTROLLER;
import static com.refinedmods.refinedstorage2.platform.common.content.ContentIds.CREATIVE_WIRELESS_GRID;
import static com.refinedmods.refinedstorage2.platform.common.content.ContentIds.DESTRUCTION_CORE;
import static com.refinedmods.refinedstorage2.platform.common.content.ContentIds.DESTRUCTOR;
import static com.refinedmods.refinedstorage2.platform.common.content.ContentIds.DETECTOR;
import static com.refinedmods.refinedstorage2.platform.common.content.ContentIds.DISK_DRIVE;
import static com.refinedmods.refinedstorage2.platform.common.content.ContentIds.EXPORTER;
import static com.refinedmods.refinedstorage2.platform.common.content.ContentIds.EXTERNAL_STORAGE;
import static com.refinedmods.refinedstorage2.platform.common.content.ContentIds.FLUID_STORAGE_BLOCK;
import static com.refinedmods.refinedstorage2.platform.common.content.ContentIds.GRID;
import static com.refinedmods.refinedstorage2.platform.common.content.ContentIds.IMPORTER;
import static com.refinedmods.refinedstorage2.platform.common.content.ContentIds.INTERFACE;
import static com.refinedmods.refinedstorage2.platform.common.content.ContentIds.ITEM_STORAGE_BLOCK;
import static com.refinedmods.refinedstorage2.platform.common.content.ContentIds.MACHINE_CASING;
import static com.refinedmods.refinedstorage2.platform.common.content.ContentIds.PROCESSOR_BINDING;
import static com.refinedmods.refinedstorage2.platform.common.content.ContentIds.QUARTZ_ENRICHED_IRON;
import static com.refinedmods.refinedstorage2.platform.common.content.ContentIds.QUARTZ_ENRICHED_IRON_BLOCK;
import static com.refinedmods.refinedstorage2.platform.common.content.ContentIds.REGULATOR_UPGRADE;
import static com.refinedmods.refinedstorage2.platform.common.content.ContentIds.SILICON;
import static com.refinedmods.refinedstorage2.platform.common.content.ContentIds.STORAGE_BLOCK;
import static com.refinedmods.refinedstorage2.platform.common.content.ContentIds.STORAGE_HOUSING;
import static com.refinedmods.refinedstorage2.platform.common.content.ContentIds.STORAGE_MONITOR;
import static com.refinedmods.refinedstorage2.platform.common.content.ContentIds.WIRELESS_GRID;
import static com.refinedmods.refinedstorage2.platform.common.content.ContentIds.WIRELESS_TRANSMITTER;
import static com.refinedmods.refinedstorage2.platform.common.content.ContentIds.WRENCH;
import static com.refinedmods.refinedstorage2.platform.common.content.ContentIds.forFluidStorageBlock;
import static com.refinedmods.refinedstorage2.platform.common.content.ContentIds.forFluidStorageDisk;
import static com.refinedmods.refinedstorage2.platform.common.content.ContentIds.forFluidStoragePart;
import static com.refinedmods.refinedstorage2.platform.common.content.ContentIds.forItemStorageBlock;
import static com.refinedmods.refinedstorage2.platform.common.content.ContentIds.forItemStoragePart;
import static com.refinedmods.refinedstorage2.platform.common.content.ContentIds.forProcessor;
import static com.refinedmods.refinedstorage2.platform.common.content.ContentIds.forStorageDisk;
import static com.refinedmods.refinedstorage2.platform.common.util.IdentifierUtil.createIdentifier;

public abstract class AbstractModInitializer {
    private static final String FLUID_REGISTRY_KEY = "fluid";

    protected final void initializePlatform(final Platform platform) {
        ((PlatformProxy) Platform.INSTANCE).setPlatform(platform);
    }

    protected final void initializePlatformApi() {
        ((PlatformApiProxy) PlatformApi.INSTANCE).setDelegate(new PlatformApiImpl());
        registerAdditionalStorageTypes();
        registerAdditionalStorageChannelTypes();
        registerAdditionalResourceFactories();
        registerDestructorStrategyFactories();
        registerConstructorStrategyFactories();
        registerStorageMonitorInsertionStrategies();
        registerStorageMonitorExtractionStrategies();
        registerNetworkComponents();
        registerWirelessTransmitterRangeModifiers();
    }

    private void registerAdditionalStorageTypes() {
        PlatformApi.INSTANCE.getStorageTypeRegistry().register(
            createIdentifier(FLUID_REGISTRY_KEY),
            StorageTypes.FLUID
        );
    }

    private void registerAdditionalStorageChannelTypes() {
        PlatformApi.INSTANCE.getStorageChannelTypeRegistry().register(
            createIdentifier(FLUID_REGISTRY_KEY),
            StorageChannelTypes.FLUID
        );
    }

    private void registerAdditionalResourceFactories() {
        PlatformApi.INSTANCE.addResourceFactory(new FluidResourceFactory());
    }

    private void registerDestructorStrategyFactories() {
        PlatformApi.INSTANCE.addDestructorStrategyFactory(new BlockBreakDestructorStrategyFactory());
        PlatformApi.INSTANCE.addDestructorStrategyFactory(new FluidBreakDestructorStrategyFactory());
        PlatformApi.INSTANCE.addDestructorStrategyFactory(new ItemPickupDestructorStrategyFactory());
    }

    private void registerConstructorStrategyFactories() {
        PlatformApi.INSTANCE.addConstructorStrategyFactory((level, pos, direction, upgradeState, dropItems) ->
            Optional.of(new PlaceBlockConstructorStrategy(level, pos, direction)));
        PlatformApi.INSTANCE.addConstructorStrategyFactory((level, pos, direction, upgradeState, dropItems) ->
            Optional.of(new PlaceFireworksConstructorStrategy(level, pos, direction)));
        PlatformApi.INSTANCE.addConstructorStrategyFactory((level, pos, direction, upgradeState, dropItems) ->
            Optional.of(new PlaceFluidConstructorStrategy(level, pos, direction)));
        PlatformApi.INSTANCE.addConstructorStrategyFactory(new ItemDropConstructorStrategyFactory());
    }

    private void registerStorageMonitorInsertionStrategies() {
        PlatformApi.INSTANCE.addStorageMonitorInsertionStrategy(new ItemStorageMonitorInsertionStrategy());
        PlatformApi.INSTANCE.addStorageMonitorInsertionStrategy(new FluidStorageMonitorInsertionStrategy());
    }

    private void registerStorageMonitorExtractionStrategies() {
        PlatformApi.INSTANCE.addStorageMonitorExtractionStrategy(new ItemStorageMonitorExtractionStrategy());
        PlatformApi.INSTANCE.addStorageMonitorExtractionStrategy(new FluidStorageMonitorExtractionStrategy());
    }

    private void registerNetworkComponents() {
        PlatformApi.INSTANCE.getNetworkComponentMapFactory().addFactory(
            EnergyNetworkComponent.class,
            network -> new EnergyNetworkComponentImpl()
        );
        PlatformApi.INSTANCE.getNetworkComponentMapFactory().addFactory(
            GraphNetworkComponent.class,
            GraphNetworkComponent::new
        );
        PlatformApi.INSTANCE.getNetworkComponentMapFactory().addFactory(
            StorageNetworkComponent.class,
            network -> new StorageNetworkComponentImpl(
                PlatformApi.INSTANCE.getStorageChannelTypeRegistry().getAll()
            )
        );
    }

    private void registerWirelessTransmitterRangeModifiers() {
        PlatformApi.INSTANCE.addWirelessTransmitterRangeModifier(new BaseWirelessTransmitterRangeModifier());
        PlatformApi.INSTANCE.addWirelessTransmitterRangeModifier(new RangeUpgradeWirelessTransmitterRangeModifier());
        PlatformApi.INSTANCE.addWirelessTransmitterRangeModifier(
            new CreativeRangeUpgradeWirelessTransmitterRangeModifier()
        );
    }

    protected final void registerBlocks(
        final RegistryCallback<Block> callback,
        final BiFunction<BlockPos, BlockState, AbstractDiskDriveBlockEntity> diskDriveBlockEntityFactory
    ) {
        Blocks.INSTANCE.setQuartzEnrichedIronBlock(callback.register(QUARTZ_ENRICHED_IRON_BLOCK, SimpleBlock::new));
        Blocks.INSTANCE.setDiskDrive(
            callback.register(DISK_DRIVE, () -> new DiskDriveBlock(diskDriveBlockEntityFactory))
        );
        Blocks.INSTANCE.setMachineCasing(callback.register(MACHINE_CASING, SimpleBlock::new));
        for (final ItemStorageType.Variant variant : ItemStorageType.Variant.values()) {
            Blocks.INSTANCE.setItemStorageBlock(variant, callback.register(
                forItemStorageBlock(variant),
                () -> new ItemStorageBlock(variant)
            ));
        }
        for (final FluidStorageType.Variant variant : FluidStorageType.Variant.values()) {
            Blocks.INSTANCE.setFluidStorageBlock(variant, callback.register(
                forFluidStorageBlock(variant),
                () -> new FluidStorageBlock(variant)
            ));
        }
        Blocks.INSTANCE.getController().registerBlocks(callback);
        Blocks.INSTANCE.getCreativeController().registerBlocks(callback);
        Blocks.INSTANCE.getCable().registerBlocks(callback);
        Blocks.INSTANCE.getGrid().registerBlocks(callback);
        Blocks.INSTANCE.getCraftingGrid().registerBlocks(callback);
        Blocks.INSTANCE.getDetector().registerBlocks(callback);
        Blocks.INSTANCE.getImporter().registerBlocks(callback);
        Blocks.INSTANCE.getExporter().registerBlocks(callback);
        Blocks.INSTANCE.getExternalStorage().registerBlocks(callback);
        Blocks.INSTANCE.getConstructor().registerBlocks(callback);
        Blocks.INSTANCE.getDestructor().registerBlocks(callback);
        Blocks.INSTANCE.setInterface(callback.register(INTERFACE, InterfaceBlock::new));
        Blocks.INSTANCE.getWirelessTransmitter().registerBlocks(callback);
        Blocks.INSTANCE.setStorageMonitor(callback.register(STORAGE_MONITOR, StorageMonitorBlock::new));
    }

    protected final void registerItems(
        final RegistryCallback<Item> callback,
        final Supplier<RegulatorUpgradeItem> regulatorUpgradeItemSupplier,
        final Supplier<WirelessGridItem> wirelessGridItemSupplier,
        final Supplier<WirelessGridItem> creativeWirelessGridItemSupplier
    ) {
        registerSimpleItems(callback);
        Blocks.INSTANCE.getGrid().registerItems(callback);
        Blocks.INSTANCE.getCraftingGrid().registerItems(callback);
        Blocks.INSTANCE.getCable().registerItems(callback, Items.INSTANCE::addCable);
        Blocks.INSTANCE.getController().registerItems(callback, Items.INSTANCE::addController);
        Blocks.INSTANCE.getCreativeController().registerItems(callback, Items.INSTANCE::addCreativeController);
        Blocks.INSTANCE.getDetector().registerItems(callback, Items.INSTANCE::addDetector);
        Blocks.INSTANCE.getImporter().registerItems(callback, Items.INSTANCE::addImporter);
        Blocks.INSTANCE.getExporter().registerItems(callback, Items.INSTANCE::addExporter);
        Blocks.INSTANCE.getExternalStorage().registerItems(callback, Items.INSTANCE::addExternalStorage);
        Blocks.INSTANCE.getConstructor().registerItems(callback, Items.INSTANCE::addConstructor);
        Blocks.INSTANCE.getDestructor().registerItems(callback, Items.INSTANCE::addDestructor);
        Blocks.INSTANCE.getWirelessTransmitter().registerItems(callback, Items.INSTANCE::addWirelessTransmitter);
        registerStorageItems(callback);
        registerUpgrades(callback, regulatorUpgradeItemSupplier);
        Items.INSTANCE.setWirelessGrid(callback.register(WIRELESS_GRID, wirelessGridItemSupplier));
        Items.INSTANCE.setCreativeWirelessGrid(callback.register(
            CREATIVE_WIRELESS_GRID,
            creativeWirelessGridItemSupplier
        ));
        callback.register(STORAGE_MONITOR, () -> new SimpleBlockItem(Blocks.INSTANCE.getStorageMonitor()));
    }

    private void registerSimpleItems(final RegistryCallback<Item> callback) {
        Items.INSTANCE.setQuartzEnrichedIron(callback.register(QUARTZ_ENRICHED_IRON, SimpleItem::new));
        callback.register(
            QUARTZ_ENRICHED_IRON_BLOCK,
            () -> new SimpleBlockItem(Blocks.INSTANCE.getQuartzEnrichedIronBlock())
        );
        Items.INSTANCE.setSilicon(callback.register(SILICON, SimpleItem::new));
        Items.INSTANCE.setProcessorBinding(callback.register(PROCESSOR_BINDING, SimpleItem::new));
        callback.register(DISK_DRIVE, () -> Blocks.INSTANCE.getDiskDrive().createBlockItem());
        Items.INSTANCE.setWrench(callback.register(WRENCH, WrenchItem::new));
        Items.INSTANCE.setStorageHousing(callback.register(STORAGE_HOUSING, SimpleItem::new));
        callback.register(MACHINE_CASING, () -> new SimpleBlockItem(Blocks.INSTANCE.getMachineCasing()));
        callback.register(INTERFACE, () -> Blocks.INSTANCE.getInterface().createBlockItem());
        Items.INSTANCE.setConstructionCore(callback.register(CONSTRUCTION_CORE, SimpleItem::new));
        Items.INSTANCE.setDestructionCore(callback.register(DESTRUCTION_CORE, SimpleItem::new));
        for (final ProcessorItem.Type type : ProcessorItem.Type.values()) {
            registerProcessor(callback, type);
        }
        Items.INSTANCE.setConfigurationCard(callback.register(
            ContentIds.CONFIGURATION_CARD,
            ConfigurationCardItem::new
        ));
    }

    private void registerProcessor(final RegistryCallback<Item> callback, final ProcessorItem.Type type) {
        Items.INSTANCE.setProcessor(type, callback.register(forProcessor(type), ProcessorItem::new));
    }

    private void registerStorageItems(final RegistryCallback<Item> callback) {
        for (final ItemStorageType.Variant variant : ItemStorageType.Variant.values()) {
            registerItemStorageItems(callback, variant);
        }
        for (final FluidStorageType.Variant variant : FluidStorageType.Variant.values()) {
            registerFluidStorageItems(callback, variant);
        }
    }

    private void registerItemStorageItems(final RegistryCallback<Item> callback,
                                          final ItemStorageType.Variant variant) {
        if (variant != ItemStorageType.Variant.CREATIVE) {
            Items.INSTANCE.setItemStoragePart(variant, callback.register(
                forItemStoragePart(variant),
                SimpleItem::new)
            );
        }
        Items.INSTANCE.setItemStorageDisk(variant, callback.register(
            forStorageDisk(variant),
            () -> new ItemStorageDiskItem(variant)
        ));
        callback.register(
            forItemStorageBlock(variant),
            () -> new ItemStorageBlockBlockItem(Blocks.INSTANCE.getItemStorageBlock(variant), variant)
        );
    }

    private void registerFluidStorageItems(final RegistryCallback<Item> callback,
                                           final FluidStorageType.Variant variant) {
        if (variant != FluidStorageType.Variant.CREATIVE) {
            Items.INSTANCE.setFluidStoragePart(variant, callback.register(
                forFluidStoragePart(variant),
                SimpleItem::new)
            );
        }
        Items.INSTANCE.setFluidStorageDisk(variant, callback.register(
            forFluidStorageDisk(variant),
            () -> new FluidStorageDiskItem(variant)
        ));
        callback.register(
            forFluidStorageBlock(variant),
            () -> new FluidStorageBlockBlockItem(Blocks.INSTANCE.getFluidStorageBlock(variant), variant)
        );
    }

    private void registerUpgrades(
        final RegistryCallback<Item> callback,
        final Supplier<RegulatorUpgradeItem> regulatorUpgradeItemSupplier
    ) {
        Items.INSTANCE.setUpgrade(callback.register(
            ContentIds.UPGRADE,
            SimpleItem::new
        ));
        final Supplier<SimpleUpgradeItem> speedUpgrade = callback.register(
            ContentIds.SPEED_UPGRADE,
            () -> new SimpleUpgradeItem(
                PlatformApi.INSTANCE.getUpgradeRegistry(),
                Platform.INSTANCE.getConfig().getUpgrade()::getSpeedUpgradeEnergyUsage,
                false
            )
        );
        Items.INSTANCE.setSpeedUpgrade(speedUpgrade);
        final Supplier<SimpleUpgradeItem> stackUpgrade = callback.register(
            ContentIds.STACK_UPGRADE,
            () -> new SimpleUpgradeItem(
                PlatformApi.INSTANCE.getUpgradeRegistry(),
                Platform.INSTANCE.getConfig().getUpgrade()::getStackUpgradeEnergyUsage,
                false
            )
        );
        Items.INSTANCE.setStackUpgrade(stackUpgrade);
        final Supplier<FortuneUpgradeItem> fortune1Upgrade = callback.register(
            ContentIds.FORTUNE_1_UPGRADE,
            () -> new FortuneUpgradeItem(PlatformApi.INSTANCE.getUpgradeRegistry(), 1)
        );
        Items.INSTANCE.setFortune1Upgrade(fortune1Upgrade);
        final Supplier<FortuneUpgradeItem> fortune2Upgrade = callback.register(
            ContentIds.FORTUNE_2_UPGRADE,
            () -> new FortuneUpgradeItem(PlatformApi.INSTANCE.getUpgradeRegistry(), 2)
        );
        Items.INSTANCE.setFortune2Upgrade(fortune2Upgrade);
        final Supplier<FortuneUpgradeItem> fortune3Upgrade = callback.register(
            ContentIds.FORTUNE_3_UPGRADE,
            () -> new FortuneUpgradeItem(PlatformApi.INSTANCE.getUpgradeRegistry(), 3)
        );
        Items.INSTANCE.setFortune3Upgrade(fortune3Upgrade);
        final Supplier<SimpleUpgradeItem> silkTouchUpgrade = callback.register(
            ContentIds.SILK_TOUCH_UPGRADE,
            () -> new SimpleUpgradeItem(
                PlatformApi.INSTANCE.getUpgradeRegistry(),
                Platform.INSTANCE.getConfig().getUpgrade()::getSilkTouchUpgradeEnergyUsage,
                true
            )
        );
        Items.INSTANCE.setSilkTouchUpgrade(silkTouchUpgrade);
        Items.INSTANCE.setRegulatorUpgrade(callback.register(
            ContentIds.REGULATOR_UPGRADE,
            regulatorUpgradeItemSupplier
        ));
        Items.INSTANCE.setRangeUpgrade(callback.register(
            ContentIds.RANGE_UPGRADE,
            () -> new RangeUpgradeItem(PlatformApi.INSTANCE.getUpgradeRegistry(), false)
        ));
        Items.INSTANCE.setCreativeRangeUpgrade(callback.register(
            ContentIds.CREATIVE_RANGE_UPGRADE,
            () -> new RangeUpgradeItem(PlatformApi.INSTANCE.getUpgradeRegistry(), true)
        ));
    }

    protected final void registerUpgradeMappings() {
        PlatformApi.INSTANCE.getUpgradeRegistry().forDestination(UpgradeDestinations.IMPORTER)
            .add(Items.INSTANCE.getSpeedUpgrade(), 4)
            .add(Items.INSTANCE.getStackUpgrade())
            .add(Items.INSTANCE.getRegulatorUpgrade());

        PlatformApi.INSTANCE.getUpgradeRegistry().forDestination(UpgradeDestinations.EXPORTER)
            .add(Items.INSTANCE.getSpeedUpgrade(), 4)
            .add(Items.INSTANCE.getStackUpgrade())
            .add(Items.INSTANCE.getRegulatorUpgrade());

        PlatformApi.INSTANCE.getUpgradeRegistry().forDestination(UpgradeDestinations.DESTRUCTOR)
            .add(Items.INSTANCE.getSpeedUpgrade(), 4)
            .add(Items.INSTANCE.getFortune1Upgrade())
            .add(Items.INSTANCE.getFortune2Upgrade())
            .add(Items.INSTANCE.getFortune3Upgrade())
            .add(Items.INSTANCE.getSilkTouchUpgrade());

        PlatformApi.INSTANCE.getUpgradeRegistry().forDestination(UpgradeDestinations.CONSTRUCTOR)
            .add(Items.INSTANCE.getSpeedUpgrade(), 4)
            .add(Items.INSTANCE.getStackUpgrade());

        PlatformApi.INSTANCE.getUpgradeRegistry().forDestination(UpgradeDestinations.WIRELESS_TRANSMITTER)
            .add(Items.INSTANCE.getRangeUpgrade(), 4)
            .add(Items.INSTANCE.getCreativeRangeUpgrade());
    }

    protected final void registerBlockEntities(
        final RegistryCallback<BlockEntityType<?>> callback,
        final BlockEntityTypeFactory typeFactory,
        final BlockEntityTypeFactory.BlockEntitySupplier<? extends AbstractDiskDriveBlockEntity>
            diskDriveBlockEntitySupplier
    ) {
        BlockEntities.INSTANCE.setCable(callback.register(
            CABLE,
            () -> typeFactory.create(CableBlockEntity::new, Blocks.INSTANCE.getCable().toArray())
        ));
        BlockEntities.INSTANCE.setController(callback.register(
            CONTROLLER,
            () -> typeFactory.create(
                (pos, state) -> new ControllerBlockEntity(ControllerType.NORMAL, pos, state),
                Blocks.INSTANCE.getController().toArray()
            )
        ));
        BlockEntities.INSTANCE.setCreativeController(callback.register(
            CREATIVE_CONTROLLER,
            () -> typeFactory.create(
                (pos, state) -> new ControllerBlockEntity(ControllerType.CREATIVE, pos, state),
                Blocks.INSTANCE.getCreativeController().toArray()
            )
        ));
        BlockEntities.INSTANCE.setDiskDrive(callback.register(
            DISK_DRIVE,
            () -> typeFactory.create(diskDriveBlockEntitySupplier, Blocks.INSTANCE.getDiskDrive())
        ));
        BlockEntities.INSTANCE.setGrid(callback.register(
            GRID,
            () -> typeFactory.create(GridBlockEntity::new, Blocks.INSTANCE.getGrid().toArray())
        ));
        BlockEntities.INSTANCE.setCraftingGrid(callback.register(
            CRAFTING_GRID,
            () -> typeFactory.create(CraftingGridBlockEntity::new, Blocks.INSTANCE.getCraftingGrid().toArray())
        ));
        for (final ItemStorageType.Variant variant : ItemStorageType.Variant.values()) {
            BlockEntities.INSTANCE.setItemStorageBlock(variant, callback.register(
                forItemStorageBlock(variant),
                () -> typeFactory.create(
                    (pos, state) -> new ItemStorageBlockBlockEntity(pos, state, variant),
                    Blocks.INSTANCE.getItemStorageBlock(variant)
                )
            ));
        }
        for (final FluidStorageType.Variant variant : FluidStorageType.Variant.values()) {
            BlockEntities.INSTANCE.setFluidStorageBlock(variant, callback.register(
                forFluidStorageBlock(variant),
                () -> typeFactory.create(
                    (pos, state) -> new FluidStorageBlockBlockEntity(pos, state, variant),
                    Blocks.INSTANCE.getFluidStorageBlock(variant)
                )
            ));
        }
        BlockEntities.INSTANCE.setImporter(callback.register(
            IMPORTER,
            () -> typeFactory.create(ImporterBlockEntity::new, Blocks.INSTANCE.getImporter().toArray())

        ));
        BlockEntities.INSTANCE.setExporter(callback.register(
            EXPORTER,
            () -> typeFactory.create(ExporterBlockEntity::new, Blocks.INSTANCE.getExporter().toArray())

        ));
        BlockEntities.INSTANCE.setInterface(callback.register(
            INTERFACE,
            () -> typeFactory.create(InterfaceBlockEntity::new, Blocks.INSTANCE.getInterface())
        ));
        BlockEntities.INSTANCE.setExternalStorage(callback.register(
            EXTERNAL_STORAGE,
            () -> typeFactory.create(ExternalStorageBlockEntity::new, Blocks.INSTANCE.getExternalStorage().toArray())
        ));
        BlockEntities.INSTANCE.setDetector(callback.register(
            DETECTOR,
            () -> typeFactory.create(DetectorBlockEntity::new, Blocks.INSTANCE.getDetector().toArray())
        ));
        BlockEntities.INSTANCE.setConstructor(callback.register(
            CONSTRUCTOR,
            () -> typeFactory.create(ConstructorBlockEntity::new, Blocks.INSTANCE.getConstructor().toArray())
        ));
        BlockEntities.INSTANCE.setDestructor(callback.register(
            DESTRUCTOR,
            () -> typeFactory.create(DestructorBlockEntity::new, Blocks.INSTANCE.getDestructor().toArray())
        ));
        BlockEntities.INSTANCE.setWirelessTransmitter(callback.register(
            WIRELESS_TRANSMITTER,
            () -> typeFactory.create(
                WirelessTransmitterBlockEntity::new,
                Blocks.INSTANCE.getWirelessTransmitter().toArray()
            )
        ));
        BlockEntities.INSTANCE.setStorageMonitor(callback.register(
            STORAGE_MONITOR,
            () -> typeFactory.create(StorageMonitorBlockEntity::new, Blocks.INSTANCE.getStorageMonitor())
        ));
    }

    protected final void registerMenus(final RegistryCallback<MenuType<?>> callback,
                                       final MenuTypeFactory menuTypeFactory) {
        Menus.INSTANCE.setController(callback.register(
            CONTROLLER,
            () -> menuTypeFactory.create(ControllerContainerMenu::new)
        ));
        Menus.INSTANCE.setDiskDrive(callback.register(
            DISK_DRIVE,
            () -> menuTypeFactory.create(DiskDriveContainerMenu::new)
        ));
        Menus.INSTANCE.setGrid(callback.register(
            GRID,
            () -> menuTypeFactory.create(GridContainerMenu::new)
        ));
        Menus.INSTANCE.setCraftingGrid(callback.register(
            CRAFTING_GRID,
            () -> menuTypeFactory.create(CraftingGridContainerMenu::new)
        ));
        Menus.INSTANCE.setWirelessGrid(callback.register(
            WIRELESS_GRID,
            () -> menuTypeFactory.create(WirelessGridContainerMenu::new)
        ));
        Menus.INSTANCE.setItemStorage(callback.register(
            ITEM_STORAGE_BLOCK,
            () -> menuTypeFactory.create(ItemStorageBlockContainerMenu::new)
        ));
        Menus.INSTANCE.setFluidStorage(callback.register(
            FLUID_STORAGE_BLOCK,
            () -> menuTypeFactory.create(FluidStorageBlockContainerMenu::new)
        ));
        Menus.INSTANCE.setImporter(callback.register(
            IMPORTER,
            () -> menuTypeFactory.create(ImporterContainerMenu::new)
        ));
        Menus.INSTANCE.setExporter(callback.register(
            EXPORTER,
            () -> menuTypeFactory.create(ExporterContainerMenu::new)
        ));
        Menus.INSTANCE.setInterface(callback.register(
            INTERFACE,
            () -> menuTypeFactory.create(InterfaceContainerMenu::new)
        ));
        Menus.INSTANCE.setExternalStorage(callback.register(
            EXTERNAL_STORAGE,
            () -> menuTypeFactory.create(ExternalStorageContainerMenu::new)
        ));
        Menus.INSTANCE.setDetector(callback.register(
            DETECTOR,
            () -> menuTypeFactory.create(DetectorContainerMenu::new)
        ));
        Menus.INSTANCE.setDestructor(callback.register(
            DESTRUCTOR,
            () -> menuTypeFactory.create(DestructorContainerMenu::new)
        ));
        Menus.INSTANCE.setConstructor(callback.register(
            CONSTRUCTOR,
            () -> menuTypeFactory.create(ConstructorContainerMenu::new)
        ));
        Menus.INSTANCE.setRegulatorUpgrade(callback.register(
            REGULATOR_UPGRADE,
            () -> menuTypeFactory.create(RegulatorUpgradeContainerMenu::new)
        ));
        Menus.INSTANCE.setWirelessTransmitter(callback.register(
            WIRELESS_TRANSMITTER,
            () -> menuTypeFactory.create(WirelessTransmitterContainerMenu::new)
        ));
        Menus.INSTANCE.setStorageMonitor(callback.register(
            STORAGE_MONITOR,
            () -> menuTypeFactory.create(StorageMonitorContainerMenu::new)
        ));
    }

    protected final void registerLootFunctions(final RegistryCallback<LootItemFunctionType> callback) {
        LootFunctions.INSTANCE.setStorageBlock(callback.register(
            STORAGE_BLOCK,
            () -> new LootItemFunctionType(new StorageBlockLootItemFunctionSerializer())
        ));
        LootFunctions.INSTANCE.setEnergy(callback.register(
            createIdentifier("energy"),
            () -> new LootItemFunctionType(new EnergyLootItemFunctionSerializer())
        ));
    }

    protected final void registerSounds(final RegistryCallback<SoundEvent> callback) {
        Sounds.INSTANCE.setWrench(callback.register(
            WRENCH,
            () -> SoundEvent.createVariableRangeEvent(WRENCH)
        ));
    }

    protected final void registerRecipeSerializers(final RegistryCallback<RecipeSerializer<?>> callback) {
        callback.register(
            createIdentifier("upgrade_with_enchanted_book"),
            UpgradeWithEnchantedBookRecipeSerializer::new
        );
    }

    protected static boolean allowNbtUpdateAnimation(final ItemStack oldStack, final ItemStack newStack) {
        return oldStack.getItem() != newStack.getItem();
    }
}
