package com.zegreatrob.couplingadventure.engine

import com.zegreatrob.minassert.assertIsEqualTo
import com.zegreatrob.testmints.setup
import kotlin.test.Test

class CreateCharacterTest {

    @Test
    fun performCreateCharacter() = setup(object : CreateCharacterDispatcher {
        val player = Player(name = "Tim")
        val people = People.Dwarf
        val heroClass = HeroClass.Warrior
    }) exercise {
        CreateCharacter(player, people, heroClass)
                .perform()
    } verify { result ->
        result.assertIsEqualTo(
                Character(
                        player,
                        people,
                        heroClass,
                        heroClass.characterSheet
                )
        )
    }

}