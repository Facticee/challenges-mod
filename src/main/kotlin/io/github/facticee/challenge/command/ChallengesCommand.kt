package io.github.facticee.challenge.command

import com.mojang.brigadier.CommandDispatcher
import io.github.facticee.challenge.gui.ChallengeScreen
import net.fabricmc.fabric.api.client.command.v2.ClientCommands
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource
import net.minecraft.client.Minecraft

object ChallengesCommand {

    fun register(dispatcher: CommandDispatcher<FabricClientCommandSource>) {
        dispatcher.register(
            ClientCommands.literal("challenges").executes { context ->
                val mc = Minecraft.getInstance()
                mc.execute { mc.setScreen(ChallengeScreen()) }
                1
            }
        )
    }
}