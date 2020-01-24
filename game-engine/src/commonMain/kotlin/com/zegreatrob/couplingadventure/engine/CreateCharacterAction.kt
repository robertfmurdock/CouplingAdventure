package com.zegreatrob.couplingadventure.engine

data class CreateCharacterAction(val player: Player, val people: People, val heroClass: HeroClass)

interface CreateCharacterActionDispatcher {
    fun CreateCharacterAction.perform() = Character(player, people, heroClass, heroClass.characterSheet)
}