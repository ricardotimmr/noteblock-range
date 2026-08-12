package de.ricardo.noteblockrange.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.ricardo.noteblockrange.NoteBlockRangeMod;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

public final class NoteBlockRangeConfigManager {
	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("noteblockrange.json");

	private static NoteBlockRangeConfig config = new NoteBlockRangeConfig();

	private NoteBlockRangeConfigManager() {
	}

	public static NoteBlockRangeConfig getConfig() {
		return config;
	}

	public static void load() {
		if (!Files.exists(CONFIG_PATH)) {
			save();
			return;
		}

		try (Reader reader = Files.newBufferedReader(CONFIG_PATH)) {
			NoteBlockRangeConfig loadedConfig = GSON.fromJson(reader, NoteBlockRangeConfig.class);
			config = loadedConfig == null ? new NoteBlockRangeConfig() : loadedConfig;
			config.sanitize();
		} catch (IOException | RuntimeException exception) {
			NoteBlockRangeMod.LOGGER.warn("Could not load config from {}", CONFIG_PATH, exception);
			config = new NoteBlockRangeConfig();
		}

		save();
	}

	public static void save() {
		try {
			Path parent = CONFIG_PATH.getParent();
			if (parent != null) {
				Files.createDirectories(parent);
			}

			try (Writer writer = Files.newBufferedWriter(CONFIG_PATH)) {
				GSON.toJson(config, writer);
			}
		} catch (IOException exception) {
			NoteBlockRangeMod.LOGGER.warn("Could not save config to {}", CONFIG_PATH, exception);
		}
	}
}
