package de.ricardo.noteblockrange.client;

import de.ricardo.noteblockrange.NoteBlockRangeMod;
import de.ricardo.noteblockrange.config.NoteBlockRangeConfigManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Client-side mod initializer. This is where client-only features are set up.
 */
@Environment(EnvType.CLIENT)
public class NoteBlockRangeClient implements ClientModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger(NoteBlockRangeMod.MOD_NAME + " [Client]");

	@Override
	public void onInitializeClient() {
		NoteBlockRangeConfigManager.load();
		LOGGER.info("Note Block Range mod initialized on client!");
	}
}
