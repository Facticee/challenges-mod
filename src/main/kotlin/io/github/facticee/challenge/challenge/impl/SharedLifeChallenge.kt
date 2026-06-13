package io.github.facticee.challenge.challenge.impl

import io.github.facticee.challenge.challenge.Challenge
import io.github.facticee.challenge.state.ChallengeState
import net.minecraft.server.level.ServerLevel
import net.minecraft.server.level.ServerPlayer

object SharedLifeChallenge : Challenge {

    override val id = "shared_life"
    override val displayName = "Shared Damage"
    override val description = "Every damage a player earns is shared among all players online"

    var isDistributing = false

    override fun onEnable() { ChallengeState.activeChallenge = this }
    override fun onDisable() {
        if (ChallengeState.activeChallenge == this) ChallengeState.activeChallenge = null
    }

    fun onPlayerHurt(damaged: ServerPlayer, amount: Float) {
        if (isDistributing) return

        val serverLevel = damaged.level() as? ServerLevel ?: return
        val server = serverLevel.server

        try {
            isDistributing = true

            for (other in server.playerList.players) {
                if (other == damaged) continue

                val otherLevel = other.level() as? ServerLevel ?: continue

                other.hurtServer(otherLevel, other.damageSources().generic(), amount)
            }
        } finally {
            isDistributing = false
        }
    }
}