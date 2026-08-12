package de.ricardo.noteblockrange.mixin;

import de.ricardo.noteblockrange.util.NoteBlockSoundHandler;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerLevel.class)
public class ServerLevelMixin {
	@Unique
	private static final ThreadLocal<Holder<SoundEvent>> noteblockrange$currentSound = new ThreadLocal<>();

	@Inject(
		method = "playSeededSound(Lnet/minecraft/world/entity/Entity;DDDLnet/minecraft/core/Holder;Lnet/minecraft/sounds/SoundSource;FFJ)V",
		at = @At("HEAD")
	)
	private void noteblockrange$rememberSound(Entity player, double x, double y, double z, Holder<SoundEvent> soundEvent,
											  SoundSource soundSource, float volume, float pitch, long seed, CallbackInfo ci) {
		noteblockrange$currentSound.set(soundEvent);
	}

	@Inject(
		method = "playSeededSound(Lnet/minecraft/world/entity/Entity;DDDLnet/minecraft/core/Holder;Lnet/minecraft/sounds/SoundSource;FFJ)V",
		at = @At("RETURN")
	)
	private void noteblockrange$clearSound(Entity player, double x, double y, double z, Holder<SoundEvent> soundEvent,
										   SoundSource soundSource, float volume, float pitch, long seed, CallbackInfo ci) {
		noteblockrange$currentSound.remove();
	}

	@Redirect(
		method = "playSeededSound(Lnet/minecraft/world/entity/Entity;DDDLnet/minecraft/core/Holder;Lnet/minecraft/sounds/SoundSource;FFJ)V",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/sounds/SoundEvent;getRange(F)F"
		)
	)
	private float noteblockrange$extendNoteBlockPacketRange(SoundEvent soundEvent, float volume) {
		Holder<SoundEvent> currentSound = noteblockrange$currentSound.get();
		if (currentSound != null && NoteBlockSoundHandler.isNoteBlockSound(currentSound)) {
			return Math.max(soundEvent.getRange(volume), NoteBlockSoundHandler.configuredRangeBlocks());
		}

		return soundEvent.getRange(volume);
	}
}
