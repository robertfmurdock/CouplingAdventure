package com.zegreatrob.couplingadventure.cli

import com.zegreatrob.couplingadventure.engine.HeroClass
import com.zegreatrob.couplingadventure.engine.People
import com.zegreatrob.couplingadventure.engine.People.valueOf
import com.zegreatrob.couplingadventure.engine.People.values
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
                    InputRequest("First, you'll need to identify yourselves. You, on the left... are what is your name?"),
                    InputRequest("And who is your people?", People.values().map(::toPresentationString)),
                    InputRequest("Oh, and what is your training?", HeroClass.values().map(::toPresentationString))
            ),
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
                valueOf(people),
                HeroClass.valueOf(heroClass)
        ).perform()
    }

}
