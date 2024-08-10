package com.refinedmods.refinedstorage.common.networking;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

import static com.refinedmods.refinedstorage.common.util.IdentifierUtil.createIdentifier;

class TransmittingIcon {
    private static final int WIDTH_0 = 11;
    private static final int WIDTH_3 = 20;
    private static final int TRANSMITTING_FRAMES = 20;
    private static final ResourceLocation NOT_TRANSMITTING = createIdentifier("transmitting/0");
    private static final ResourceLocation TRANSMITTING_1 = createIdentifier("transmitting/1");
    private static final ResourceLocation TRANSMITTING_2 = createIdentifier("transmitting/2");
    private static final ResourceLocation TRANSMITTING_3 = createIdentifier("transmitting/3");

    private int frames;
    private int cycle;
    private boolean active;

    TransmittingIcon(final boolean active) {
        this.active = active;
    }

    void tick(final boolean newActive) {
        this.active = newActive;
        doTick();
    }

    private void doTick() {
        if (!active) {
            frames = 0;
            cycle = 0;
            return;
        }
        ++frames;
        if (frames == TRANSMITTING_FRAMES) {
            frames = 0;
            cycle++;
        }
    }

    void render(final GuiGraphics graphics, final int x3, final int y3) {
        if (!active) {
            graphics.blitSprite(NOT_TRANSMITTING, x3, y3 + 4, WIDTH_0, 4);
            return;
        }
        final int frame = cycle % 3;
        switch (frame) {
            case 0:
                graphics.blitSprite(TRANSMITTING_1, x3, y3 + 3, 14, 6);
                break;
            case 1:
                graphics.blitSprite(TRANSMITTING_2, x3, y3 + 1, 17, 10);
                break;
            case 2:
                graphics.blitSprite(TRANSMITTING_3, x3, y3, WIDTH_3, 12);
                break;
        }
    }

    int getWidth() {
        return active ? WIDTH_3 : WIDTH_0;
    }
}
