package de.ricardo.noteblockrange.mixin;

import de.ricardo.noteblockrange.util.NoteBlockSoundHandler;
import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundEngine;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SoundEngine.class)
public class SoundEngineMixin {
	@Unique
	private static final ThreadLocal<SoundInstance> noteblockrange$currentSound = new ThreadLocal<>();

	@Inject(method = "play", at = @At("HEAD"))
	private void noteblockrange$rememberSound(SoundInstance soundInstance, CallbackInfoReturnable<SoundEngine.PlayResult> cir) {
		noteblockrange$currentSound.set(soundInstance);
	}

	@Inject(method = "play", at = @At("RETURN"))
	private void noteblockrange$clearSound(SoundInstance soundInstance, CallbackInfoReturnable<SoundEngine.PlayResult> cir) {
		noteblockrange$currentSound.remove();
	}

	@Redirect(
		method = "play",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/resources/sounds/Sound;getAttenuationDistance()I"
		)
	)
	private int noteblockrange$extendNoteBlockAttenuationDistance(Sound sound) {
		SoundInstance soundInstance = noteblockrange$currentSound.get();
		if (soundInstance == null) {
			return sound.getAttenuationDistance();
		}

		return NoteBlockSoundHandler.attenuationDistanceForRange(sound.getAttenuationDistance(), soundInstance);
	}
}
