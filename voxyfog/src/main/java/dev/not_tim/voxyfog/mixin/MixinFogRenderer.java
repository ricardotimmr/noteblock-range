package dev.not_tim.voxyfog.mixin;

import dev.not_tim.voxyfog.VoxyCompat;
import dev.not_tim.voxyfog.VoxyFogTuning;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.fog.FogData;
import net.minecraft.client.renderer.fog.FogRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = FogRenderer.class, priority = 900)
public abstract class MixinFogRenderer {
    @Inject(
            method = "setupFog",
            at = @At("RETURN")
    )
    private void voxyfog$applyVoxyDistanceFog(
            Camera camera,
            int renderDistance,
            DeltaTracker deltaTracker,
            float partialTick,
            ClientLevel level,
            CallbackInfoReturnable<FogData> cir
    ) {
        VoxyCompat.State state = VoxyCompat.getState();
        if (!state.isActive()) {
            return;
        }

        FogData fogData = cir.getReturnValue();
        if (fogData.environmentalEnd < 16.0f) {
            return;
        }

        float vanillaDistanceBlocks = renderDistance * 16.0f;
        VoxyFogTuning.FogRange fogRange = VoxyFogTuning.compute(state.renderDistanceBlocks(), vanillaDistanceBlocks);
        if (fogRange == null) {
            return;
        }

        fogData.environmentalStart = fogRange.start();
        fogData.environmentalEnd = fogRange.end();
    }
}
