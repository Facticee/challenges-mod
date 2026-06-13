package io.github.facticee.challenge.randomizer

import io.github.facticee.challenge.state.ChallengeState
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block

object BlockDropRandomizer {

    private val dropMap: MutableMap<Block, Item> = mutableMapOf()

    fun getDropFor(block: Block): Item {
        return dropMap.getOrPut(block) { randomItem() }
    }


    fun reset() {
        dropMap.clear()
    }

    val isActive: Boolean
        get() = ChallengeState.randomizerEnabled

    private fun randomItem(): Item {
        val items = BuiltInRegistries.ITEM.stream().toList()
        return items.random()
    }
}