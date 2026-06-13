package io.github.facticee.challenge.challenge

import io.github.facticee.challenge.challenge.impl.IceTrailChallenge
import io.github.facticee.challenge.challenge.impl.JumpItemChallenge
import io.github.facticee.challenge.challenge.impl.SharedLifeChallenge

object ChallengeRegistry {

    val all: List<Challenge> = listOf(
        SharedLifeChallenge,
        JumpItemChallenge,
        IceTrailChallenge,
    )

    fun getById(id: String): Challenge? = all.find { it.id == id }
}