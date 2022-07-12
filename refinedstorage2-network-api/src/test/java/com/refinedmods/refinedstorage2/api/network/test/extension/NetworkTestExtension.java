package com.refinedmods.refinedstorage2.api.network.test.extension;

import com.refinedmods.refinedstorage2.api.core.Action;
import com.refinedmods.refinedstorage2.api.network.Network;
import com.refinedmods.refinedstorage2.api.network.NetworkImpl;
import com.refinedmods.refinedstorage2.api.network.component.EnergyNetworkComponent;
import com.refinedmods.refinedstorage2.api.network.component.StorageNetworkComponent;
import com.refinedmods.refinedstorage2.api.network.energy.EnergyStorage;
import com.refinedmods.refinedstorage2.api.network.energy.EnergyStorageImpl;
import com.refinedmods.refinedstorage2.api.network.node.NetworkNode;
import com.refinedmods.refinedstorage2.api.network.node.controller.ControllerNetworkNode;
import com.refinedmods.refinedstorage2.api.network.node.diskdrive.DiskDriveNetworkNode;
import com.refinedmods.refinedstorage2.api.network.node.grid.GridNetworkNode;
import com.refinedmods.refinedstorage2.api.network.node.importer.ImporterNetworkNode;
import com.refinedmods.refinedstorage2.api.network.node.storage.StorageNetworkNode;
import com.refinedmods.refinedstorage2.api.network.test.NetworkTestFixtures;
import com.refinedmods.refinedstorage2.api.network.test.SpyingNetworkNode;
import com.refinedmods.refinedstorage2.api.storage.channel.StorageChannel;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

public class NetworkTestExtension implements BeforeEachCallback, AfterEachCallback, ParameterResolver {
    private final Map<String, Network> networkMap = new HashMap<>();

    public NetworkTestExtension() {
    }

    @Override
    public void beforeEach(final ExtensionContext extensionContext) {
        extensionContext
            .getTestInstances()
            .ifPresent(testInstances -> testInstances.getAllInstances().forEach(this::processTestInstance));
    }

    @Override
    public void afterEach(final ExtensionContext extensionContext) {
        networkMap.clear();
    }

    private void processTestInstance(final Object testInstance) {
        setupNetworks(testInstance);
        injectNetworks(testInstance);
        addNetworkNodes(testInstance);
    }

    private void setupNetworks(final Object testInstance) {
        final SetupNetwork[] annotations = testInstance.getClass().getAnnotationsByType(SetupNetwork.class);
        for (final SetupNetwork annotation : annotations) {
            final Network network = new NetworkImpl(NetworkTestFixtures.NETWORK_COMPONENT_MAP_FACTORY);
            setupNetworkEnergy(annotation.energyCapacity(), annotation.energyStored(), network);
            networkMap.put(annotation.id(), network);
        }
    }

    private void setupNetworkEnergy(final long capacity, final long stored, final Network network) {
        final EnergyNetworkComponent component = network.getComponent(EnergyNetworkComponent.class);
        final EnergyStorage storage = new EnergyStorageImpl(capacity);
        storage.receive(stored, Action.EXECUTE);
        final ControllerNetworkNode controller = new ControllerNetworkNode();
        controller.setEnergyStorage(storage);
        component.onContainerAdded(() -> controller);
    }

    private void injectNetworks(final Object testInstance) {
        final Field[] fields = testInstance.getClass().getDeclaredFields();
        for (final Field field : fields) {
            final InjectNetwork annotation = field.getAnnotation(InjectNetwork.class);
            if (annotation != null) {
                final Network network = networkMap.get(annotation.value());
                setField(testInstance, field, network);
            }
        }
    }

    private void addNetworkNodes(final Object testInstance) {
        final Field[] fields = testInstance.getClass().getDeclaredFields();
        for (final Field field : fields) {
            tryAddSimpleNetworkNode(testInstance, field);
            tryAddDiskDrive(testInstance, field);
        }
    }

    private void tryAddDiskDrive(final Object testInstance, final Field field) {
        final AddDiskDrive annotation = field.getAnnotation(AddDiskDrive.class);
        if (annotation != null) {
            final NetworkNode resolvedNode = new DiskDriveNetworkNode(
                annotation.baseEnergyUsage(),
                annotation.energyUsagePerDisk(),
                NetworkTestFixtures.STORAGE_CHANNEL_TYPE_REGISTRY
            );
            final Network network = networkMap.get(annotation.networkId());
            registerNetworkNode(testInstance, field, resolvedNode, network);
        }
    }

    private void tryAddSimpleNetworkNode(final Object testInstance, final Field field) {
        final AddNetworkNode annotation = field.getAnnotation(AddNetworkNode.class);
        if (annotation != null) {
            final NetworkNode resolvedNode = resolveSimpleNetworkNode(field.getType(), annotation.energyUsage());
            final Network network = networkMap.get(annotation.networkId());
            registerNetworkNode(testInstance, field, resolvedNode, network);
        }
    }

    private NetworkNode resolveSimpleNetworkNode(final Class<?> type, final long energyUsage) {
        if (type == StorageNetworkNode.class) {
            return new StorageNetworkNode<>(energyUsage, NetworkTestFixtures.STORAGE_CHANNEL_TYPE);
        } else if (type == SpyingNetworkNode.class) {
            return new SpyingNetworkNode(energyUsage);
        } else if (type == GridNetworkNode.class) {
            return new GridNetworkNode<>(energyUsage, NetworkTestFixtures.STORAGE_CHANNEL_TYPE);
        } else if (type == ImporterNetworkNode.class) {
            return new ImporterNetworkNode(energyUsage);
        }
        throw new RuntimeException(type.getName());
    }

    private void registerNetworkNode(final Object testInstance,
                                     final Field field,
                                     final NetworkNode networkNode,
                                     final Network network) {
        networkNode.setNetwork(network);
        network.addContainer(() -> networkNode);
        setField(testInstance, field, networkNode);
    }

    private void setField(final Object instance, final Field field, final Object value) {
        try {
            field.setAccessible(true);
            field.set(instance, value);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean supportsParameter(final ParameterContext parameterContext,
                                     final ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.isAnnotated(InjectNetworkStorageChannel.class)
            || parameterContext.isAnnotated(InjectNetworkEnergyComponent.class);
    }

    @Override
    public Object resolveParameter(final ParameterContext parameterContext,
                                   final ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext
            .findAnnotation(InjectNetworkStorageChannel.class)
            .map(annotation -> (Object) getNetworkStorageChannel(annotation.networkId()))
            .or(() -> parameterContext
                .findAnnotation(InjectNetworkEnergyComponent.class)
                .map(annotation -> (Object) getNetworkEnergy(annotation.networkId())))
            .orElseThrow();
    }

    private StorageChannel<String> getNetworkStorageChannel(final String networkId) {
        return networkMap
            .get(networkId)
            .getComponent(StorageNetworkComponent.class)
            .getStorageChannel(NetworkTestFixtures.STORAGE_CHANNEL_TYPE);
    }

    private EnergyNetworkComponent getNetworkEnergy(final String networkId) {
        return networkMap
            .get(networkId)
            .getComponent(EnergyNetworkComponent.class);
    }
}
