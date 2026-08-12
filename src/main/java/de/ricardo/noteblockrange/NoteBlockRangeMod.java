package de.ricardo.noteblockrange;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main mod initializer - runs on both client and server (though this mod is client-only).
 */
public class NoteBlockRangeMod implements ModInitializer {
	public static final String MOD_ID = "noteblockrange";
	public static final String MOD_NAME = "Note Block Range";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

	@Override
	public void onInitialize() {
		LOGGER.info("Initializing {} mod...", MOD_NAME);
	}
}
