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

        val inputRequest = InputRequest("Select one", listOf("A", "B", "C"))
        val gamePlan: List<(GameState) -> CLICommandBuilder?> = listOf { _ ->
            CLICommandBuilder(listOf(inputRequest)) { GameSetupState(emptyList()) }
        }

        val askSpy = SpyData<InputRequest, String>().apply {
            spyWillReturn(listOf("C"))
        }

        override fun InputRequest.ask() = askSpy.spyFunction(this).also { exitRequested = true }

        val userMessages = mutableListOf<String>()
        override fun String.sendToUser() = Unit.also { userMessages += this }
    }) exercise {
        CLIGameRunner(gamePlan).perform()
    } verify { _ ->
        askSpy.spyReceivedValues.assertContains(inputRequest)
    }

    @Test
    fun willUseGameStateFromFirstBuilderToFindNextBuilder() = setup(object : CLIGameRunnerPerformer {
        override var exitRequested: Boolean = false

        val expectedMiddleState = GameSetupState(listOf(stubCharacter()))
        val expectedEndState = GameSetupState(listOf(stubCharacter(), stubCharacter()))

        val stateToCommandSpy = SpyData<GameState, CLICommandBuilder?>().apply {
            whenever(GameSetupState(), CLICommandBuilder(emptyList()) { expectedMiddleState })
            whenever(expectedMiddleState, CLICommandBuilder(emptyList()) { expectedEndState })
            whenever(expectedEndState, null)
        }

        val gamePlan: List<(GameState) -> CLICommandBuilder?> = listOf(
                stateToCommandSpy::spyFunction,
                stateToCommandSpy::spyFunction
        )

        override fun InputRequest.ask() = ""

        val userMessages = mutableListOf<String>()
        override fun String.sendToUser() = Unit.also { userMessages += this }
    }) exercise {
        CLIGameRunner(gamePlan).perform()
    } verify { result ->
        result.assertIsEqualTo(expectedEndState)

        stateToCommandSpy.spyReceivedValues
                .assertIsEqualTo(
                        listOf(GameSetupState(), expectedMiddleState, expectedEndState, expectedEndState)
                )
    }


}
