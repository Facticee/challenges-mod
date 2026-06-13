package io.github.facticee.challenge.state

import io.github.facticee.challenge.challenge.Challenge

object ChallengeState {
    var activeChallenge: Challenge? = null
    var randomizerEnabled: Boolean = false
}