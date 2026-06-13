package io.github.facticee.challenge.gui

import io.github.facticee.challenge.challenge.Challenge
import io.github.facticee.challenge.challenge.ChallengeRegistry
import io.github.facticee.challenge.randomizer.BlockDropRandomizer
import io.github.facticee.challenge.state.ChallengeState
import net.minecraft.client.gui.GuiGraphicsExtractor
import net.minecraft.client.gui.components.Button
import net.minecraft.client.gui.screens.Screen
import net.minecraft.network.chat.Component
import net.minecraft.util.ARGB

class ChallengeScreen : Screen(Component.literal("✦ Challenges ✦")) {

    override fun init() {
        val centerX = width / 2
        var y = height / 4

        for (challenge in ChallengeRegistry.all) {
            val isActive = ChallengeState.activeChallenge?.id == challenge.id
            val label = if (isActive) "✔ ${challenge.displayName}" else challenge.displayName

            addRenderableWidget(
                Button.builder(Component.literal(label)) {
                    if (isActive) {
                        challenge.onDisable()
                    } else {
                        ChallengeState.activeChallenge?.onDisable()
                        challenge.onEnable()
                    }
                    minecraft?.setScreen(ChallengeScreen())
                }
                    .pos(centerX - 100, y)
                    .size(200, 20)
                    .build()
            )
            y += 24
        }

        y += 10

        val randomizerLabel = if (ChallengeState.randomizerEnabled)
            "🎲 Randomizer: On"
        else
            "🎲 Randomizer: Off"

        addRenderableWidget(
            Button.builder(Component.literal(randomizerLabel)) {
                ChallengeState.randomizerEnabled = !ChallengeState.randomizerEnabled
                BlockDropRandomizer.reset()
                minecraft?.setScreen(ChallengeScreen())
            }
                .pos(centerX - 100, y)
                .size(200, 20)
                .build()
        )

        y += 30

        addRenderableWidget(
            Button.builder(Component.literal("Close")) { onClose() }
                .pos(centerX - 60, y)
                .size(120, 20)
                .build()
        )
    }

    override fun extractRenderState(
        graphics: GuiGraphicsExtractor,
        mouseX: Int,
        mouseY: Int,
        partialTick: Float
    ) {
        super.extractRenderState(graphics, mouseX, mouseY, partialTick)

        val titleStr = title.string
        val titleX = (width - font.width(titleStr)) / 2

        graphics.text(font, titleStr, titleX, height / 4 - 20, ARGB.opaque(0xFFFFFF), false)

        val active: Challenge? = ChallengeState.activeChallenge
        if (active != null) {
            val descX = (width - font.width(active.description)) / 2
            graphics.text(font, active.description, descX, height / 4 - 8, ARGB.opaque(0xAAAAAA), false)
        }
    }

    override fun isPauseScreen() = false
}