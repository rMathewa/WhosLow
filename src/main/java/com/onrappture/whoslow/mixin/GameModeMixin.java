package com.onrappture.whoslow.mixin;
import com.onrappture.whoslow.GlowState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
@Mixin(MultiPlayerGameMode.class)
public class GameModeMixin {
    @Inject(method = "attack", at = @At("HEAD"))
    private void whoslow_onAttack(Player player, Entity target, CallbackInfo ci) {
        if (target instanceof Player targetPlayer) {
            GlowState.ATTACKED_PLAYERS.put(targetPlayer.getUUID(), System.currentTimeMillis());
        }
    }
}
// #made by mathew love u all <3
