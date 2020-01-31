package com.zegreatrob.couplingadventure.cli

import com.zegreatrob.minassert.assertIsEqualTo
import com.zegreatrob.testmints.setup
import org.junit.jupiter.api.Test

class CLIGameRunnerTest {

    @Test
    fun whenGamePlanIsEmptyWillEndGame() = setup(object : CLIGameRunnerPerformer {
        override var exitRequested: Boolean = false

        val gamePlan = emptyList<(GameState) -> CLICommandBuilder?>()

        val userMessages = mutableListOf<String>()
        override fun String.sendToUser() = Unit.also { userMessages += this }

    }) exercise {
        CLIGameRunner(gamePlan).perform()
    } verify { result ->
        result.assertIsEqualTo(GameSetupState())
        userMessages.assertIsEqualTo(listOf("Looks like that's the end!"))
    }

}