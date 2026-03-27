package com.onrappture.whoslow.mixin;

import com.onrappture.whoslow.GlowConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class GlowMixin {

    @Inject(method = "isCurrentlyGlowing", at = @At("HEAD"), cancellable = true)
    private void whoslow_forceGlow(CallbackInfoReturnable<Boolean> cir) {
        if (GlowConfig.get().isEnabled() && (Object) this instanceof Player targetPlayer) {
            Player localPlayer = Minecraft.getInstance().player;
            if (localPlayer != null && targetPlayer != localPlayer) {
                if (!targetPlayer.isAlive() || targetPlayer.getHealth() <= 0.0f || targetPlayer.isRemoved()) {
                    com.onrappture.whoslow.GlowState.ATTACKED_PLAYERS.remove(targetPlayer.getUUID());
                } else if (targetPlayer.getHealth() >= targetPlayer.getMaxHealth()) {
                    Long hitTime = com.onrappture.whoslow.GlowState.ATTACKED_PLAYERS.get(targetPlayer.getUUID());
                    if (hitTime != null && System.currentTimeMillis() - hitTime > 1500L) {
                        com.onrappture.whoslow.GlowState.ATTACKED_PLAYERS.remove(targetPlayer.getUUID());
                    }
                }
                if (!com.onrappture.whoslow.GlowState.ATTACKED_PLAYERS.containsKey(targetPlayer.getUUID())) {
                    return;
                }
                if (GlowConfig.get().isLineOfSightOnly() && !localPlayer.hasLineOfSight(targetPlayer)) {
                    return;
                }

                cir.setReturnValue(true);
            }
        }
    }

    @Inject(method = "getTeamColor", at = @At("HEAD"), cancellable = true)
    private void whoslow_glowColor(CallbackInfoReturnable<Integer> cir) {
        if (GlowConfig.get().isEnabled() && (Object) this instanceof Player player) {
            Player localPlayer = Minecraft.getInstance().player;
            if (localPlayer != null && player != localPlayer) {
                if (!player.isAlive() || player.getHealth() <= 0.0f || player.isRemoved()) {
                    com.onrappture.whoslow.GlowState.ATTACKED_PLAYERS.remove(player.getUUID());
                } else if (player.getHealth() >= player.getMaxHealth()) {
                    Long hitTime = com.onrappture.whoslow.GlowState.ATTACKED_PLAYERS.get(player.getUUID());
                    if (hitTime != null && System.currentTimeMillis() - hitTime > 1500L) {
                        com.onrappture.whoslow.GlowState.ATTACKED_PLAYERS.remove(player.getUUID());
                    }
                }
                if (!com.onrappture.whoslow.GlowState.ATTACKED_PLAYERS.containsKey(player.getUUID())) return;
                if (GlowConfig.get().isLineOfSightOnly() && !localPlayer.hasLineOfSight(player)) return;

                if (player.getHealth() < localPlayer.getHealth()) {
                    cir.setReturnValue(GlowConfig.get().getLowerHealthColor());
                } else if (player.getHealth() == localPlayer.getHealth()) {
                    cir.setReturnValue(GlowConfig.get().getEqualHealthColor());
                } else {
                    cir.setReturnValue(GlowConfig.get().getHigherHealthColor());
                }
            }
        }
    }
}

// #made by mathew love u all <3
