package com.zegreatrob.couplingadventure.cli

import com.zegreatrob.minassert.assertContains
import com.zegreatrob.minassert.assertIsEqualTo
import com.zegreatrob.minspy.SpyData
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

    @Test
    fun willHandleCommandBuildersWithMultipleChoice() = setup(object : CLIGameRunnerPerformer {
        override var exitRequested: Boolean = false

        val commandBuilderSpy = SpyData<List<String>, GameSetupState>()
        val inputRequest = InputRequest("Select one", listOf("A", "B", "C"))
        val gamePlan: List<(GameState) -> CLICommandBuilder?> = listOf { _ ->
            CLICommandBuilder(listOf(inputRequest), commandBuilderSpy::spyFunction)
        }

        val askSpy = SpyData<InputRequest, String>().apply {
            spyWillReturn(listOf("C"))
        }

        override fun InputRequest.ask() = askSpy.spyFunction(this)

        val userMessages = mutableListOf<String>()
        override fun String.sendToUser() = Unit.also { userMessages += this }
    }) exercise {
        CLIGameRunner(gamePlan).perform()
    } verify { _ ->
        askSpy.spyReceivedValues.assertContains(inputRequest)
    }

}