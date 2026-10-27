package dev.not_tim.voxyfog;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

public final class VoxyFogConfig {
    public static final int MIN_FOG_START_CHUNKS = 2;
    public static final int MAX_FOG_START_CHUNKS = 256;
    public static final int DEFAULT_FOG_START_CHUNKS = 48;
    private static final int LEGACY_PERCENT_BASE_CHUNKS = 64;

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("voxy-renderdistance-fog.json");

    private static State state = new State();

    private VoxyFogConfig() {
    }

    public static void load() {
        if (!Files.exists(CONFIG_PATH)) {
            save();
            return;
        }

        try (Reader reader = Files.newBufferedReader(CONFIG_PATH)) {
            State loaded = GSON.fromJson(reader, State.class);
            if (loaded != null) {
                state = loaded;
                state.sanitize();
            }
        } catch (IOException ignored) {
        }

        save();
    }

    public static void save() {
        try {
            Files.createDirectories(CONFIG_PATH.getParent());
            try (Writer writer = Files.newBufferedWriter(CONFIG_PATH)) {
                GSON.toJson(state, writer);
            }
        } catch (IOException ignored) {
        }
    }

    public static boolean isEnabled() {
        return state.enabled;
    }

    public static void setEnabled(boolean enabled) {
        state.enabled = enabled;
        save();
    }

    public static int getFogStartChunks() {
        return state.fogStartChunks;
    }

    public static float getFogStartBlocks() {
        return state.fogStartChunks * 16.0f;
    }

    public static void setFogStartChunks(int fogStartChunks) {
        state.fogStartChunks = clampFogStartChunks(fogStartChunks);
        save();
    }

    public static int clampFogStartChunks(int fogStartChunks) {
        return Math.max(MIN_FOG_START_CHUNKS, Math.min(MAX_FOG_START_CHUNKS, fogStartChunks));
    }

    private static final class State {
        private boolean enabled = true;
        private int fogStartChunks = DEFAULT_FOG_START_CHUNKS;
        private Integer fogStartPercent;

        private void sanitize() {
            if (this.fogStartChunks == 0 && this.fogStartPercent != null) {
                this.fogStartChunks = Math.round(this.fogStartPercent / 100.0f * LEGACY_PERCENT_BASE_CHUNKS);
            }

            this.fogStartChunks = clampFogStartChunks(this.fogStartChunks);
            this.fogStartPercent = null;
        }
    }
}
