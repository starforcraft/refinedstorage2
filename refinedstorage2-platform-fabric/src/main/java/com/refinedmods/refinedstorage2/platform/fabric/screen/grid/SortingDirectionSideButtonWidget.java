package com.refinedmods.refinedstorage2.platform.fabric.screen.grid;

import com.refinedmods.refinedstorage2.api.grid.view.GridSortingDirection;
import com.refinedmods.refinedstorage2.platform.fabric.Rs2Mod;
import com.refinedmods.refinedstorage2.platform.fabric.screen.TooltipRenderer;
import com.refinedmods.refinedstorage2.platform.fabric.screen.widget.SideButtonWidget;
import com.refinedmods.refinedstorage2.platform.fabric.screenhandler.grid.GridScreenHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class SortingDirectionSideButtonWidget extends SideButtonWidget {
    private final GridScreenHandler screenHandler;
    private final TooltipRenderer tooltipRenderer;
    private final Map<GridSortingDirection, List<Text>> tooltips = new EnumMap<>(GridSortingDirection.class);

    public SortingDirectionSideButtonWidget(GridScreenHandler screenHandler, TooltipRenderer tooltipRenderer) {
        super(createPressAction(screenHandler));
        this.screenHandler = screenHandler;
        this.tooltipRenderer = tooltipRenderer;
        Arrays.stream(GridSortingDirection.values()).forEach(type -> tooltips.put(type, calculateTooltip(type)));
    }

    private static PressAction createPressAction(GridScreenHandler screenHandler) {
        return btn -> screenHandler.setSortingDirection(screenHandler.getSortingDirection().toggle());
    }

    private List<Text> calculateTooltip(GridSortingDirection type) {
        List<Text> lines = new ArrayList<>();
        lines.add(Rs2Mod.createTranslation("gui", "grid.sorting.direction"));
        lines.add(Rs2Mod.createTranslation("gui", "grid.sorting.direction." + type.toString().toLowerCase(Locale.ROOT)).formatted(Formatting.GRAY));
        return lines;
    }

    @Override
    protected int getXTexture() {
        return screenHandler.getSortingDirection() == GridSortingDirection.ASCENDING ? 0 : 16;
    }

    @Override
    protected int getYTexture() {
        return 16;
    }

    @Override
    public void onTooltip(ButtonWidget buttonWidget, MatrixStack matrixStack, int mouseX, int mouseY) {
        tooltipRenderer.render(matrixStack, tooltips.get(screenHandler.getSortingDirection()), mouseX, mouseY);
    }
}
