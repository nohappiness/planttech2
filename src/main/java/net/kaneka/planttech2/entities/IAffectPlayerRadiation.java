package net.kaneka.planttech2.entities;

import net.minecraft.entity.player.ServerPlayerEntity;

import javax.annotation.Nonnull;

public interface IAffectPlayerRadiation
{
    boolean shouldAffectPlayer();

    void onTriggerAffectingPlayer(@Nonnull ServerPlayerEntity player);
}
