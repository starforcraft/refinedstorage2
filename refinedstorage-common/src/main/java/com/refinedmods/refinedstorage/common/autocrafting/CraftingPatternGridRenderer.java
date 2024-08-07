package com.refinedmods.refinedstorage.common.autocrafting;

import com.refinedmods.refinedstorage.common.support.widget.CustomCheckboxWidget;

import java.util.function.Consumer;
import javax.annotation.Nullable;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;

import static com.refinedmods.refinedstorage.common.autocrafting.PatternGridScreen.INSET_PADDING;
import static com.refinedmods.refinedstorage.common.util.IdentifierUtil.createIdentifier;
import static com.refinedmods.refinedstorage.common.util.IdentifierUtil.createTranslation;

class CraftingPatternGridRenderer implements PatternGridRenderer {
    private static final ResourceLocation CRAFTING = createIdentifier("pattern_grid/crafting");
    private static final MutableComponent FUZZY_MODE = createTranslation("gui", "pattern_grid.fuzzy_mode");
    private static final MutableComponent FUZZY_MODE_ON_HELP =
        createTranslation("gui", "pattern_grid.fuzzy_mode.on.help");
    private static final MutableComponent FUZZY_MODE_OFF_HELP =
        createTranslation("gui", "pattern_grid.fuzzy_mode.off.help");

    @Nullable
    private CustomCheckboxWidget fuzzyModeCheckbox;

    private final PatternGridContainerMenu menu;
    private final int leftPos;
    private final int x;
    private final int y;

    CraftingPatternGridRenderer(final PatternGridContainerMenu menu, final int leftPos, final int x, final int y) {
        this.menu = menu;
        this.leftPos = leftPos;
        this.x = x;
        this.y = y;
    }

    @Override
    public void addWidgets(final Consumer<AbstractWidget> widgets, final Consumer<AbstractWidget> renderables) {
        this.fuzzyModeCheckbox = createFuzzyModeCheckbox();
        renderables.accept(fuzzyModeCheckbox);
    }

    private CustomCheckboxWidget createFuzzyModeCheckbox() {
        final CustomCheckboxWidget checkbox = new CustomCheckboxWidget(
            x + INSET_PADDING,
            y + INSET_PADDING + 54 + INSET_PADDING - 2,
            FUZZY_MODE,
            Minecraft.getInstance().font,
            menu.isFuzzyMode(),
            CustomCheckboxWidget.Size.SMALL
        );
        checkbox.setOnPressed((c, selected) -> menu.setFuzzyMode(selected));
        checkbox.setTooltip(getFuzzyModeTooltip(menu.isFuzzyMode()));
        checkbox.visible = isFuzzyModeCheckboxVisible();
        return checkbox;
    }

    private static Tooltip getFuzzyModeTooltip(final boolean fuzzyMode) {
        return fuzzyMode ? Tooltip.create(FUZZY_MODE_ON_HELP) : Tooltip.create(FUZZY_MODE_OFF_HELP);
    }

    @Override
    public int getClearButtonX() {
        return leftPos + 68;
    }

    @Override
    public int getClearButtonY() {
        return y + INSET_PADDING;
    }

    @Override
    public void patternTypeChanged(final PatternType newPatternType) {
        if (fuzzyModeCheckbox != null) {
            fuzzyModeCheckbox.visible = isFuzzyModeCheckboxVisible();
        }
    }

    private boolean isFuzzyModeCheckboxVisible() {
        return menu.getPatternType() == PatternType.CRAFTING;
    }

    @Override
    public void fuzzyModeChanged(final boolean newFuzzyMode) {
        if (fuzzyModeCheckbox == null) {
            return;
        }
        fuzzyModeCheckbox.setSelected(newFuzzyMode);
        fuzzyModeCheckbox.setTooltip(getFuzzyModeTooltip(newFuzzyMode));
    }

    @Override
    public void renderBackground(final GuiGraphics graphics,
                                 final float partialTicks,
                                 final int mouseX,
                                 final int mouseY) {
        graphics.blitSprite(CRAFTING, x + INSET_PADDING, y + INSET_PADDING, 130, 54);
    }
}
