package com.zegreatrob.couplingadventure.cli

import com.zegreatrob.couplingadventure.engine.*

data class CreateCharacterCommand(val player: Player, val people: People, val heroClass: HeroClass)

interface CreateCharacterCommandDispatcher : CreateCharacterActionDispatcher {

    fun CreateCharacterCommand.perform(state: GameSetupState) = run {
        val character = createCharacter(player, people, heroClass)
        state.copy(players = state.players + character)
    }

    private fun createCharacter(name: Player, people: People, heroClass: HeroClass) = CreateCharacterAction(
            name,
            people,
            heroClass
    ).perform()
}
