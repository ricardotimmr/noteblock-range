package de.ricardo.noteblockrange.mixin;

import de.ricardo.noteblockrange.util.NoteBlockSoundHandler;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.sound.SoundManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Mixin for SoundManager to intercept sound playback and modify note block sounds.
 */
@Mixin(SoundManager.class)
public class SoundManagerMixin {
	
	@Inject(
		method = "play(Lnet/minecraft/client/sound/SoundInstance;)V",
		at = @At("HEAD")
	)
	private void onPlaySound(SoundInstance sound, CallbackInfo ci) {
		// Modify note block sounds when they are played
		if (NoteBlockSoundHandler.isNoteBlockSound(sound)) {
			NoteBlockSoundHandler.modifySoundInstance(sound);
		}
	}
}
