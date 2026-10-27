package dev.not_tim.voxyfog.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import dev.not_tim.voxyfog.VoxyCompat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;

@Pseudo
@Mixin(targets = "me.cortex.voxy.client.core.NormalRenderPipeline", remap = false)
public abstract class MixinNormalRenderPipeline {
    @ModifyExpressionValue(
            method = "finish",
            at = @At(
                    value = "INVOKE",
                    target = "Lme/cortex/voxy/client/core/VoxyRenderSystem;getVanillaRenderDistance()F",
                    ordinal = 1
            )
    )
    private float voxyfog$extendVoxyInternalFogCap(float original) {
        VoxyCompat.State state = VoxyCompat.getState();
        if (!state.isActive()) {
            return original;
        }

        return Math.max(original, state.renderDistanceBlocks());
    }
}
