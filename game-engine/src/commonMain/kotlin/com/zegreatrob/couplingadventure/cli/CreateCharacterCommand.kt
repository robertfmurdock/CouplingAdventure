package com.zegreatrob.couplingadventure.cli

import com.zegreatrob.couplingadventure.engine.*

data class CreateCharacterCommand(val name: String, val people: String, val heroClass: String)

interface CreateCharacterCommandDispatcher : CreateCharacterActionDispatcher {

    fun CreateCharacterCommand.perform() = run {
        val character = createCharacter(name, people, heroClass)
        GameSetupState(listOf(character))
    }

    private fun createCharacter(name: String, people: String, heroClass: String) = CreateCharacterAction(
            Player(name),
            People.valueOf(people),
            HeroClass.valueOf(heroClass)
    ).perform()
}
