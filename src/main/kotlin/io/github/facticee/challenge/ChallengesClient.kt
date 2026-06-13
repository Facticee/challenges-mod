package io.github.facticee.challenge

import io.github.facticee.challenge.challenge.impl.IceTrailChallenge
import io.github.facticee.challenge.command.ChallengesCommand
import io.github.facticee.challenge.randomizer.BlockDropRandomizer
import io.github.facticee.challenge.state.ChallengeState
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents


object ChallengesClient : ClientModInitializer {

    override fun onInitializeClient() {

        ClientCommandRegistrationCallback.EVENT.register { dispatcher, _ ->
            ChallengesCommand.register(dispatcher)
        }


        ClientTickEvents.END_CLIENT_TICK.register { client ->
            val player = client.player ?: return@register
            if (ChallengeState.activeChallenge !is IceTrailChallenge) return@register

            val sneaking = client.options.keyShift.isDown
            if (sneaking && !IceTrailChallenge.wasShiftDown) {
                val uuid = player.stringUUID
                if (uuid in IceTrailChallenge.activePlayers) {
                    IceTrailChallenge.activePlayers.remove(uuid)
                } else {
                    IceTrailChallenge.activePlayers.add(uuid)
                }
            }
            IceTrailChallenge.wasShiftDown = sneaking
        }


        PlayerBlockBreakEvents.AFTER.register { world, player, pos, state, _ ->
            if (!BlockDropRandomizer.isActive) return@register
            val randomItem = BlockDropRandomizer.getDropFor(state.block)
            val itemEntity = net.minecraft.world.entity.item.ItemEntity(
                world,
                pos.x + 0.5,
                pos.y + 0.5,
                pos.z + 0.5,
                randomItem.defaultInstance
            )
            world.addFreshEntity(itemEntity)
        }
    }
}