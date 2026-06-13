package io.github.facticee.challenge

import io.github.facticee.challenge.challenge.impl.IceTrailChallenge
import io.github.facticee.challenge.state.ChallengeState
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents
import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerLevel
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.level.block.Blocks
import org.slf4j.LoggerFactory

object Challenges : ModInitializer {

    const val MOD_ID = "challenges"
    val logger = LoggerFactory.getLogger(MOD_ID)

    override fun onInitialize() {
        logger.info("Challenges Mod geladen!")

        ServerTickEvents.END_SERVER_TICK.register { server ->
            if (ChallengeState.activeChallenge !is IceTrailChallenge) return@register

            for (player in server.playerList.players) {
                if (player.stringUUID !in IceTrailChallenge.activePlayers) continue

                val level = player.level() as? ServerLevel ?: continue

                val vehicle = player.vehicle
                val pos = if (vehicle != null) vehicle.blockPosition() else player.blockPosition()

                val radius = if (vehicle != null) 2 else 1
                val yOffset = -1

                for (dx in -radius..radius) {
                    for (dz in -radius..radius) {
                        val target = BlockPos(pos.x + dx, pos.y + yOffset, pos.z + dz)
                        if (level.getBlockState(target).isAir) {
                            level.setBlock(target, Blocks.ICE.defaultBlockState(), 3)
                        }
                    }
                }
            }
        }
    }
}