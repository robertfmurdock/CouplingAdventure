package com.zegreatrob.couplingadventure.cli

import com.zegreatrob.couplingadventure.GamePlan
import com.zegreatrob.couplingadventure.Transition.AddCharacter
import com.zegreatrob.couplingadventure.Transition.StartAdventure
import com.zegreatrob.couplingadventure.engine.HeroClass
import com.zegreatrob.couplingadventure.engine.People
import com.zegreatrob.couplingadventure.engine.Player

fun main() = commandDispatcher { MainCommand.perform() }

fun commandDispatcher(function: MainCommandDispatcher.() -> Unit) {
    val commandDispatcher = object : MainCommandDispatcher {
        override var exitRequested: Boolean = false
    }
    commandDispatcher.function()
}

object MainCommand

interface MainCommandDispatcher : OutputSyntax, InputRequestSyntax, CLIGameRunnerPerformer, CreateCharacterCommandDispatcher {

    val cliGamePlan: List<(GameState) -> CLICommandBuilder?>
        get() = listOf(
                { state ->
                    val transitions = GamePlan.nextTransition(state)
                    when (transitions.first()) {
                        AddCharacter -> chooseCharacterBuilder(state)
                        StartAdventure -> startAdventureBuilder(state)
                    }
                }
        )

    private fun chooseCharacterBuilder(state: GameState) = if (state is GameSetupState) {
        if (state.players.isEmpty())
            createFirstCharacterBuilder(state)
        else
            createSecondCharacterBuilder(state)
    } else
        null

    private fun startAdventureBuilder(state: GameState): CLICommandBuilder? = null

    private fun createFirstCharacterBuilder(state: GameSetupState) = CLICommandBuilder(
            inputRequests = listOf(
                    InputRequest("First, you'll need to identify yourselves. You, on the left... are what is your name?"),
                    InputRequest("And who is your people?", People.values().map(::toPresentationString)),
                    InputRequest("Oh, and what is your training?", HeroClass.values().map(::toPresentationString))
            ),
            commandFunction = performCreateCharacterCommand(state)
    )

    private fun createSecondCharacterBuilder(state: GameSetupState) = CLICommandBuilder(
            inputRequests = listOf(
                    InputRequest("Alright, how about on the right? Name please!"),
                    InputRequest("And your people is...?", People.values().map(::toPresentationString)),
                    InputRequest("What's your adventuring trade?", HeroClass.values().map(::toPresentationString))
            ),
            commandFunction = performCreateCharacterCommand(state)
    )

    fun MainCommand.perform() {
        "Welcome to Coupling Adventure!"
                .sendToUser()
        CLIGameRunner(cliGamePlan).perform()
    }

    private fun performCreateCharacterCommand(state: GameSetupState) = { inputs: List<String> ->
        val (name, people, heroClass) = inputs
        "Lovely, welcome $name the $people $heroClass!".sendToUser()

        CreateCharacterCommand(
                Player(name),
                people.toPeople(),
                heroClass.toHeroClass()
        ).perform(state)
    }

}
