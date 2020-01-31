package com.zegreatrob.couplingadventure.cli

import com.zegreatrob.couplingadventure.engine.HeroClass
import com.zegreatrob.couplingadventure.engine.People
import com.zegreatrob.couplingadventure.engine.Player

fun main() = commandDispatcher {
    MainCommand.perform()
}

fun commandDispatcher(function: MainCommandDispatcher.() -> Unit) {
    val commandDispatcher = object : MainCommandDispatcher {
        override var exitRequested: Boolean = false
    }
    commandDispatcher.function()
}

object MainCommand

interface MainCommandDispatcher : OutputSyntax, InputRequestSyntax, CLIGameRunnerPerformer,
        CreateCharacterCommandDispatcher {

    val cliGamePlan: List<(GameState) -> CLICommandBuilder?>
        get() = listOf(
                { state ->
                    createCharacterBuilder()
                }
        )

    private fun createCharacterBuilder() = CLICommandBuilder(
            inputRequests = listOf(
                    "First, you'll need to identify yourselves. You, on the left... are what is your name?",
                    "And who is your people?",
                    "Oh, and what is your training?"
            ).map { InputRequest(it) },
            commandFunction = ::performCreateCharacterCommand
    )

    fun MainCommand.perform() {
        "Welcome to Coupling Adventure!"
                .sendToUser()
        CLIGameRunner(cliGamePlan).perform()
    }

    private fun performCreateCharacterCommand(inputs: List<String>): GameSetupState {
        val (name, people, heroClass) = inputs
        "Lovely, welcome $name the $people $heroClass!".sendToUser()

        return CreateCharacterCommand(
                Player(name),
                People.valueOf(people),
                HeroClass.valueOf(heroClass)
        ).perform()
    }


}
