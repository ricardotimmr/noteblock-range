package de.ricardo.noteblockrange.client.gui;

import de.ricardo.noteblockrange.config.NoteBlockRangeConfig;
import de.ricardo.noteblockrange.config.NoteBlockRangeConfigManager;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public final class NoteBlockRangeConfigScreen extends Screen {
	private static final Component TITLE = Component.literal("Note Block Range");

	private final Screen parent;
	private RangeSliderButton rangeSlider;

	public NoteBlockRangeConfigScreen(Screen parent) {
		super(TITLE);
		this.parent = parent;
	}

	@Override
	protected void init() {
		int centerX = width / 2;
		int rowY = height / 2 - 28;
		int controlWidth = 260;

		rangeSlider = addRenderableWidget(new RangeSliderButton(centerX - controlWidth / 2, rowY, controlWidth, 20,
			NoteBlockRangeConfigManager.getConfig().getRangeBlocks(), this::saveRange));

		addRenderableWidget(Button.builder(Component.literal("Reset"), button -> rangeSlider.setRangeBlocks(NoteBlockRangeConfig.DEFAULT_RANGE_BLOCKS))
			.bounds(centerX - controlWidth / 2, rowY + 28, 124, 20)
			.build());

		addRenderableWidget(Button.builder(Component.literal("Done"), button -> onClose())
			.bounds(centerX + controlWidth / 2 - 124, rowY + 28, 124, 20)
			.build());
	}

	@Override
	public void extractRenderState(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
		super.extractRenderState(guiGraphics, mouseX, mouseY, partialTick);
		guiGraphics.centeredText(font, title, width / 2, height / 2 - 62, 0xFFFFFFFF);
		guiGraphics.centeredText(font, Component.literal("Affects received vanilla note block sounds only."), width / 2, height / 2 + 28, 0xFFA0A0A0);
	}

	@Override
	public void onClose() {
		if (minecraft != null) {
			minecraft.gui.setScreen(parent);
		}
	}

	private void saveRange(int rangeBlocks) {
		NoteBlockRangeConfigManager.getConfig().setRangeBlocks(rangeBlocks);
		NoteBlockRangeConfigManager.save();
	}
}
