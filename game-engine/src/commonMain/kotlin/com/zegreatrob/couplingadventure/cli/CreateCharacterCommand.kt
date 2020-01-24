package com.zegreatrob.couplingadventure.cli

import com.zegreatrob.couplingadventure.engine.*

data class CreateCharacterCommand(val prompt: String)

interface CreateCharacterCommandDispatcher : InputRequestSyntax, CreateCharacterActionDispatcher {
    fun CreateCharacterCommand.perform(): GameSetupState {
        val name = prompt
                .waitForResponse()
        val people = waitForResponse()
        val heroClass = waitForResponse()

        "Lovely, welcome $name the $people $heroClass!"
                .sendToUser()

        return if (name != null && people != null && heroClass != null) {
            val character = CreateCharacterAction(Player(name), People.valueOf(people), HeroClass.valueOf(heroClass))
                    .perform()
            GameSetupState(listOf(character))
        } else {
            GameSetupState()
        }

    }
}
