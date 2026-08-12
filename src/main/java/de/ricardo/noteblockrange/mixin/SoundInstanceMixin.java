package de.ricardo.noteblockrange.mixin;

import de.ricardo.noteblockrange.util.NoteBlockSoundHandler;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.sounds.SoundEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Mixin to modify SoundInstance behavior for note block sounds.
 * Increases the attenuation distance for note block sounds.
 */
@Mixin(SoundInstance.class)
public class SoundInstanceMixin {
	
	@Inject(
		method = "<init>",
		at = @At("TAIL")
	)
	private void onSoundInstanceCreated(CallbackInfo ci) {
		SoundInstance instance = (SoundInstance) (Object) this;
		
		// Check if this is a note block sound
		if (NoteBlockSoundHandler.isNoteBlockSound(instance)) {
			NoteBlockSoundHandler.modifySoundInstance(instance);
		}
	}
}
