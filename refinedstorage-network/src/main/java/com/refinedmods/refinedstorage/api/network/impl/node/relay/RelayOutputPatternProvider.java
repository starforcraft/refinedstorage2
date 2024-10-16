package com.refinedmods.refinedstorage.api.network.impl.node.relay;

import com.refinedmods.refinedstorage.api.autocrafting.Pattern;
import com.refinedmods.refinedstorage.api.network.autocrafting.AutocraftingNetworkComponent;
import com.refinedmods.refinedstorage.api.network.autocrafting.ParentContainer;
import com.refinedmods.refinedstorage.api.network.autocrafting.PatternListener;
import com.refinedmods.refinedstorage.api.network.autocrafting.PatternProvider;
import com.refinedmods.refinedstorage.api.resource.ResourceKey;
import com.refinedmods.refinedstorage.api.resource.filter.Filter;
import com.refinedmods.refinedstorage.api.resource.filter.FilterMode;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import javax.annotation.Nullable;

class RelayOutputPatternProvider implements PatternProvider, PatternListener {
    private final Filter filter = new Filter();
    private final Set<ParentContainer> parents = new HashSet<>();
    @Nullable
    private AutocraftingNetworkComponent delegate;

    void setFilters(final Set<ResourceKey> filters) {
        reset(() -> filter.setFilters(filters));
    }

    void setFilterMode(final FilterMode filterMode) {
        reset(() -> filter.setMode(filterMode));
    }

    void setFilterNormalizer(final UnaryOperator<ResourceKey> normalizer) {
        reset(() -> filter.setNormalizer(normalizer));
    }

    private void reset(final Runnable action) {
        final AutocraftingNetworkComponent oldDelegate = delegate;
        setDelegate(null);
        action.run();
        setDelegate(oldDelegate);
    }

    void setDelegate(@Nullable final AutocraftingNetworkComponent delegate) {
        if (this.delegate != null) {
            parents.forEach(parent -> getPatterns().forEach(parent::remove));
            this.delegate.removeListener(this);
        }
        this.delegate = delegate;
        if (delegate != null) {
            parents.forEach(parent -> getPatterns().forEach(parent::add));
            delegate.addListener(this);
        }
    }

    boolean hasDelegate() {
        return delegate != null;
    }

    private Set<Pattern> getPatterns() {
        if (delegate == null) {
            return Collections.emptySet();
        }
        return delegate.getPatterns().stream().filter(this::isPatternAllowed).collect(Collectors.toSet());
    }

    private boolean isPatternAllowed(final Pattern pattern) {
        return pattern.getOutputResources().stream().anyMatch(filter::isAllowed);
    }

    @Override
    public void onAdded(final Pattern pattern) {
        if (delegate == null || !isPatternAllowed(pattern) || delegate.contains(delegate)) {
            return;
        }
        parents.forEach(parent -> parent.add(pattern));
    }

    @Override
    public void onRemoved(final Pattern pattern) {
        if (delegate == null || !isPatternAllowed(pattern) || delegate.contains(delegate)) {
            return;
        }
        parents.forEach(parent -> parent.remove(pattern));
    }

    @Override
    public boolean contains(final AutocraftingNetworkComponent component) {
        return component == delegate || (delegate != null && delegate.contains(component));
    }

    @Override
    public void onAddedIntoContainer(final ParentContainer parentContainer) {
        if (delegate != null) {
            delegate.getPatterns().forEach(parentContainer::add);
        }
        parents.add(parentContainer);
    }

    @Override
    public void onRemovedFromContainer(final ParentContainer parentContainer) {
        if (delegate != null) {
            delegate.getPatterns().forEach(parentContainer::remove);
        }
        parents.remove(parentContainer);
    }
}
