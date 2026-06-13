package io.github.facticee.challenge.challenge.impl

import io.github.facticee.challenge.challenge.Challenge
import io.github.facticee.challenge.state.ChallengeState

object IceTrailChallenge : Challenge {
    override val id = "ice_trail"
    override val displayName = "Icewalk (press sneak once to toggle)"
    override val description = "!Sneak to toggle! Icerink under you"

    val activePlayers: MutableSet<String> = mutableSetOf()
    var wasShiftDown: Boolean = false

    override fun onEnable() { ChallengeState.activeChallenge = this }
    override fun onDisable() {
        if (ChallengeState.activeChallenge == this) {
            ChallengeState.activeChallenge = null
            activePlayers.clear()
            wasShiftDown = false
        }
    }
}