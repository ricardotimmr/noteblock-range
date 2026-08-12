package de.ricardo.noteblockrange.client.gui;

import de.ricardo.noteblockrange.config.NoteBlockRangeConfig;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.network.chat.Component;

import java.util.function.IntConsumer;

final class RangeSliderButton extends AbstractSliderButton {
	private final IntConsumer onChange;

	RangeSliderButton(int x, int y, int width, int height, int rangeBlocks, IntConsumer onChange) {
		super(x, y, width, height, Component.empty(), valueFromRange(rangeBlocks));
		this.onChange = onChange;
		updateMessage();
	}

	@Override
	protected void updateMessage() {
		setMessage(Component.literal("Range: " + getRangeBlocks() + " blocks"));
	}

	@Override
	protected void applyValue() {
		onChange.accept(getRangeBlocks());
	}

	void setRangeBlocks(int rangeBlocks) {
		value = valueFromRange(rangeBlocks);
		updateMessage();
		applyValue();
	}

	private int getRangeBlocks() {
		int min = NoteBlockRangeConfig.MIN_RANGE_BLOCKS;
		int max = NoteBlockRangeConfig.MAX_RANGE_BLOCKS;
		int steppedValue = min + (int) Math.round(value * (max - min) / 16.0) * 16;
		return NoteBlockRangeConfig.clampRange(steppedValue);
	}

	private static double valueFromRange(int rangeBlocks) {
		int min = NoteBlockRangeConfig.MIN_RANGE_BLOCKS;
		int max = NoteBlockRangeConfig.MAX_RANGE_BLOCKS;
		return (double) (NoteBlockRangeConfig.clampRange(rangeBlocks) - min) / (double) (max - min);
	}
}
