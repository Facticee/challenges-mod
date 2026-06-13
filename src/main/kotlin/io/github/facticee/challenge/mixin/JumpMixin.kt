package io.github.facticee.challenge.mixin

import io.github.facticee.challenge.challenge.impl.JumpItemChallenge
import io.github.facticee.challenge.state.ChallengeState
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.server.level.ServerLevel
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.item.ItemEntity
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo

@Mixin(LivingEntity::class)
abstract class JumpMixin {

    @Inject(method = ["jumpFromGround"], at = [At("HEAD")])
    private fun onJump(ci: CallbackInfo) {
        if (ChallengeState.activeChallenge !is JumpItemChallenge) return
        val player = this as? ServerPlayer ?: return
        val level = player.level() as? ServerLevel ?: return

        val items = BuiltInRegistries.ITEM.stream().toList()
        val randomItem = items.random()

        val itemEntity = ItemEntity(
            level,
            player.x,
            player.y,
            player.z,
            randomItem.defaultInstance
        )
        level.addFreshEntity(itemEntity)
    }
}