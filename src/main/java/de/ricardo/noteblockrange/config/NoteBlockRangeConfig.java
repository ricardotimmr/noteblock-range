package de.ricardo.noteblockrange.config;

public final class NoteBlockRangeConfig {
	public static final int MIN_RANGE_BLOCKS = 16;
	public static final int MAX_RANGE_BLOCKS = 512;
	public static final int DEFAULT_RANGE_BLOCKS = 256;

	private int rangeBlocks = DEFAULT_RANGE_BLOCKS;

	public int getRangeBlocks() {
		return rangeBlocks;
	}

	public void setRangeBlocks(int rangeBlocks) {
		this.rangeBlocks = clampRange(rangeBlocks);
	}

	public void sanitize() {
		setRangeBlocks(rangeBlocks);
	}

	public static int clampRange(int rangeBlocks) {
		return Math.max(MIN_RANGE_BLOCKS, Math.min(MAX_RANGE_BLOCKS, rangeBlocks));
	}
}
