package io.github.facticee.challenge.challenge.impl

import io.github.facticee.challenge.challenge.Challenge
import io.github.facticee.challenge.state.ChallengeState

object JumpItemChallenge : Challenge {
    override val id = "jump_item"
    override val displayName = "Jump = Random Item"
    override val description = "Every jump grants you a random item"

    override fun onEnable() { ChallengeState.activeChallenge = this }
    override fun onDisable() {
        if (ChallengeState.activeChallenge == this) ChallengeState.activeChallenge = null
    }
}