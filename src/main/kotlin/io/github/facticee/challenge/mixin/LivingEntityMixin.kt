package io.github.facticee.challenge.mixin

import io.github.facticee.challenge.challenge.impl.SharedLifeChallenge
import io.github.facticee.challenge.state.ChallengeState
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.damagesource.DamageSource
import net.minecraft.world.entity.LivingEntity
import net.minecraft.server.level.ServerPlayer
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable

@Mixin(LivingEntity::class)
abstract class LivingEntityMixin {

    @Inject(method = ["hurtServer"], at = [At("TAIL")])
    private fun onHurtServer(
        level: ServerLevel,
        source: DamageSource,
        amount: Float,
        cir: CallbackInfoReturnable<Boolean>
    ) {
        if (!cir.returnValue) return
        if (ChallengeState.activeChallenge !is SharedLifeChallenge) return

        if (SharedLifeChallenge.isDistributing) return

        val entity = this as? ServerPlayer ?: return

        SharedLifeChallenge.onPlayerHurt(entity, amount)
    }
}