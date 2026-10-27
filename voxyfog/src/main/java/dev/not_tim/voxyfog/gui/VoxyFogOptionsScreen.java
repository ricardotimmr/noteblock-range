package dev.not_tim.voxyfog.gui;

import dev.not_tim.voxyfog.VoxyFogConfig;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

public final class VoxyFogOptionsScreen extends Screen {
    private static final Component TITLE = Component.translatable("voxy_renderdistance_fog.options.title");

    private final Screen lastScreen;

    public VoxyFogOptionsScreen(Screen lastScreen) {
        super(TITLE);
        this.lastScreen = lastScreen;
    }

    @Override
    protected void init() {
        int x = this.width / 2 - 155;
        int y = this.height / 6 + 36;

        addRenderableWidget(
                CycleButton.onOffBuilder(VoxyFogConfig.isEnabled())
                        .create(
                                x,
                                y,
                                310,
                                20,
                                Component.translatable("voxy_renderdistance_fog.option.enabled"),
                                (button, value) -> VoxyFogConfig.setEnabled(value)
                        )
        );
        addRenderableWidget(new FogStartSliderButton(x, y + 24, 310, 20));
        addRenderableWidget(
                Button.builder(CommonComponents.GUI_DONE, button -> onClose())
                        .bounds(this.width / 2 - 100, this.height - 27, 200, 20)
                        .build()
        );
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.extractRenderState(guiGraphics, mouseX, mouseY, partialTick);
        guiGraphics.centeredText(this.font, this.title, this.width / 2, 20, 0xFFFFFFFF);
    }

    @Override
    public void onClose() {
        this.minecraft.gui.setScreen(this.lastScreen);
    }
}
