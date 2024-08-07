package com.refinedmods.refinedstorage.common.autocrafting;

import com.refinedmods.refinedstorage.common.api.RefinedStorageApi;
import com.refinedmods.refinedstorage.common.api.support.resource.PlatformResourceKey;
import com.refinedmods.refinedstorage.common.api.support.resource.ResourceRendering;
import com.refinedmods.refinedstorage.common.support.TextureIds;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.network.chat.Component;

import static com.refinedmods.refinedstorage.common.support.TextureIds.LIGHT_ARROW;
import static com.refinedmods.refinedstorage.common.support.TextureIds.LIGHT_ARROW_HEIGHT;
import static com.refinedmods.refinedstorage.common.support.TextureIds.LIGHT_ARROW_WIDTH;
import static java.util.Objects.requireNonNullElse;

class SmithingTablePatternClientTooltipComponent implements ClientTooltipComponent {
    private static final int ARROW_SPACING = 8;

    private final Component outputText;
    private final SmithingTablePattern pattern;

    SmithingTablePatternClientTooltipComponent(final SmithingTablePattern pattern) {
        this.outputText = getOutputText(pattern.output());
        this.pattern = pattern;
    }

    @Override
    public void renderImage(final Font font, final int x, final int y, final GuiGraphics graphics) {
        graphics.drawString(font, outputText, x, y, requireNonNullElse(ChatFormatting.GRAY.getColor(), 15));
        final int slotsY = y + 9 + 2;
        graphics.blitSprite(TextureIds.SLOT, x, slotsY, 18, 18);
        RefinedStorageApi.INSTANCE.getResourceRendering(pattern.template())
            .render(pattern.template(), graphics, x + 1, slotsY + 1);
        graphics.blitSprite(TextureIds.SLOT, x + 18, slotsY, 18, 18);
        RefinedStorageApi.INSTANCE.getResourceRendering(pattern.base())
            .render(pattern.base(), graphics, x + 18 + 1, slotsY + 1);
        graphics.blitSprite(TextureIds.SLOT, x + 18 + 18, slotsY, 18, 18);
        RefinedStorageApi.INSTANCE.getResourceRendering(pattern.addition())
            .render(pattern.addition(), graphics, x + 18 + 18 + 1, slotsY + 1);
        graphics.blitSprite(
            LIGHT_ARROW,
            x + (18 * 3) + ARROW_SPACING,
            y + 9 + 2 + (18 / 2) - (LIGHT_ARROW_HEIGHT / 2),
            LIGHT_ARROW_WIDTH,
            LIGHT_ARROW_HEIGHT
        );
        final int lastSlotX = x + (18 * 3) + ARROW_SPACING + LIGHT_ARROW_WIDTH + ARROW_SPACING;
        graphics.blitSprite(TextureIds.SLOT, lastSlotX, slotsY, 18, 18);
        RefinedStorageApi.INSTANCE.getResourceRendering(pattern.output())
            .render(pattern.output(), graphics, lastSlotX + 1, slotsY + 1);
    }

    @Override
    public int getHeight() {
        return 9 + 2 + 18 + 3;
    }

    @Override
    public int getWidth(final Font font) {
        return Math.max(
            font.width(outputText),
            (18 * 3) + ARROW_SPACING + LIGHT_ARROW_WIDTH + ARROW_SPACING + 18
        );
    }

    private static Component getOutputText(final PlatformResourceKey resource) {
        final ResourceRendering rendering = RefinedStorageApi.INSTANCE.getResourceRendering(resource);
        return Component.literal("1x ")
            .append(rendering.getDisplayName(resource))
            .withStyle(ChatFormatting.GRAY);
    }
}
