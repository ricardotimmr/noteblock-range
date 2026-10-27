package dev.not_tim.voxyfog.gui;

import dev.not_tim.voxyfog.VoxyFogConfig;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.network.chat.Component;

public final class FogStartSliderButton extends AbstractSliderButton {
    public FogStartSliderButton(int x, int y, int width, int height) {
        super(x, y, width, height, Component.empty(), valueFromChunks(VoxyFogConfig.getFogStartChunks()));
        updateMessage();
    }

    @Override
    protected void updateMessage() {
        setMessage(Component.translatable("voxy_renderdistance_fog.option.fog_start", getChunks()));
    }

    @Override
    protected void applyValue() {
        VoxyFogConfig.setFogStartChunks(getChunks());
    }

    private int getChunks() {
        int min = VoxyFogConfig.MIN_FOG_START_CHUNKS;
        int max = VoxyFogConfig.MAX_FOG_START_CHUNKS;
        int chunks = min + (int) Math.round(value * (max - min));
        return VoxyFogConfig.clampFogStartChunks(chunks);
    }

    private static double valueFromChunks(int chunks) {
        int min = VoxyFogConfig.MIN_FOG_START_CHUNKS;
        int max = VoxyFogConfig.MAX_FOG_START_CHUNKS;
        return (double) (VoxyFogConfig.clampFogStartChunks(chunks) - min) / (double) (max - min);
    }
}
