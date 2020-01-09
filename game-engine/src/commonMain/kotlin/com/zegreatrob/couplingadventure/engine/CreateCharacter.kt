package com.zegreatrob.couplingadventure.engine

data class CreateCharacter(val player: Player, val people: People, val heroClass: HeroClass)

interface CreateCharacterDispatcher {
    fun CreateCharacter.perform() = Character(player, people, heroClass)
}