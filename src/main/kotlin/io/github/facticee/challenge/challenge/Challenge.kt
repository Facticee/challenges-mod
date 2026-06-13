package io.github.facticee.challenge.challenge

interface Challenge {
    val id: String
    val displayName: String
    val description: String

    fun onEnable()
    fun onDisable()
}