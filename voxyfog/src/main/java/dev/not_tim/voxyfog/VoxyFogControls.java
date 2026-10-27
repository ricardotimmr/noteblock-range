package dev.not_tim.voxyfog;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.fabricmc.fabric.api.client.command.v2.ClientCommands;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.network.chat.Component;

public final class VoxyFogControls {
    private VoxyFogControls() {
    }

    public static void register() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> dispatcher.register(
                ClientCommands.literal("voxyfog")
                        .then(ClientCommands.literal("status")
                                .executes(context -> {
                                    VoxyCompat.State state = VoxyCompat.getState();
                                    context.getSource().sendFeedback(Component.literal(
                                            "VoxyFog enabled=" + VoxyFogConfig.isEnabled()
                                                    + ", active=" + state.isActive()
                                                    + ", fogStart=" + VoxyFogConfig.getFogStartChunks() + " chunks"
                                                    + ", voxyDistance=" + Math.round(state.renderDistanceBlocks() / 16.0f) + " chunks"
                                    ));
                                    return 1;
                                }))
                        .then(ClientCommands.literal("enabled")
                                .then(ClientCommands.argument("enabled", BoolArgumentType.bool())
                                        .executes(context -> {
                                            boolean enabled = BoolArgumentType.getBool(context, "enabled");
                                            VoxyFogConfig.setEnabled(enabled);
                                            context.getSource().sendFeedback(Component.translatable(
                                                    "voxy_renderdistance_fog.command.enabled",
                                                    enabled
                                            ));
                                            return 1;
                                        })))
                        .then(ClientCommands.literal("fogstart")
                                .then(ClientCommands.argument(
                                                "chunks",
                                                IntegerArgumentType.integer(
                                                        VoxyFogConfig.MIN_FOG_START_CHUNKS,
                                                        VoxyFogConfig.MAX_FOG_START_CHUNKS
                                                )
                                        )
                                        .executes(context -> {
                                            int chunks = IntegerArgumentType.getInteger(context, "chunks");
                                            VoxyFogConfig.setFogStartChunks(chunks);
                                            context.getSource().sendFeedback(Component.translatable(
                                                    "voxy_renderdistance_fog.command.fog_start",
                                                    chunks
                                            ));
                                            return 1;
                                        })))
        ));
    }
}
