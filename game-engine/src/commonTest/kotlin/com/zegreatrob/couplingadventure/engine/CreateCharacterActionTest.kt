package com.zegreatrob.couplingadventure.engine

import com.zegreatrob.minassert.assertIsEqualTo
import com.zegreatrob.testmints.setup
import kotlin.test.Test

class CreateCharacterActionTest {

    @Test
    fun performCreateCharacter() = setup(object : CreateCharacterActionDispatcher {
        val player = Player(name = "Tim")
        val people = People.Dwarf
        val heroClass = HeroClass.Warrior
    }) exercise {
        CreateCharacterAction(player, people, heroClass)
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