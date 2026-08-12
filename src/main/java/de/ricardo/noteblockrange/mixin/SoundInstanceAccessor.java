package de.ricardo.noteblockrange.mixin;

import net.minecraft.client.sound.SoundInstance;
import net.minecraft.sounds.SoundEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

/**
 * Accessor mixin to expose SoundInstance fields that we need to modify.
 */
@Mixin(SoundInstance.class)
public interface SoundInstanceAccessor {
	@Accessor("sound")
	SoundEvent getSound();
}
