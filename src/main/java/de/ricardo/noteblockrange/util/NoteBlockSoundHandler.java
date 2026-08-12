package de.ricardo.noteblockrange.util;

import de.ricardo.noteblockrange.config.NoteBlockRangeConfigManager;
import net.minecraft.core.Holder;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

/**
 * Detects vanilla note block sounds and calculates the client-side attenuation
 * distance needed for an intended hearing range.
 */
public final class NoteBlockSoundHandler {
	private static final String NOTE_BLOCK_NAMESPACE = "minecraft";
	private static final String NOTE_BLOCK_PATH_PREFIX = "block.note_block.";

	private NoteBlockSoundHandler() {
	}

	public static boolean isNoteBlockSound(SoundInstance soundInstance) {
		return isNoteBlockSound(soundInstance.getLocation());
	}

	public static boolean isNoteBlockSound(Holder<SoundEvent> soundEvent) {
		return isNoteBlockSound(soundEvent.value().location());
	}

	public static boolean isNoteBlockSound(ResourceLocation id) {
		return NOTE_BLOCK_NAMESPACE.equals(id.getNamespace()) && id.getPath().startsWith(NOTE_BLOCK_PATH_PREFIX);
	}

	public static float configuredRangeBlocks() {
		return NoteBlockRangeConfigManager.getConfig().getRangeBlocks();
	}

	public static int attenuationDistanceForRange(int vanillaAttenuationDistance, SoundInstance soundInstance) {
		if (!isNoteBlockSound(soundInstance)) {
			return vanillaAttenuationDistance;
		}

		float volumeMultiplier = Math.max(soundInstance.getVolume(), 1.0f);
		int extendedAttenuationDistance = (int) Math.ceil(configuredRangeBlocks() / volumeMultiplier);
		return Math.max(vanillaAttenuationDistance, extendedAttenuationDistance);
	}
}
