package de.ricardo.noteblockrange.mixin;

import de.ricardo.noteblockrange.util.NoteBlockSoundHandler;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundEngine;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(SoundEngine.class)
public class SoundEngineMixin {
	@ModifyExpressionValue(
		method = "play(Lnet/minecraft/client/resources/sounds/SoundInstance;)Lnet/minecraft/client/sounds/SoundEngine$PlayResult;",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/resources/sounds/Sound;getAttenuationDistance(F)F"
		)
	)
	private float noteblockrange$extendNoteBlockAttenuationDistance(float vanillaDistance,
			@Local(argsOnly = true) SoundInstance soundInstance) {
		return NoteBlockSoundHandler.attenuationDistanceForRange(vanillaDistance, soundInstance);
	}
}
