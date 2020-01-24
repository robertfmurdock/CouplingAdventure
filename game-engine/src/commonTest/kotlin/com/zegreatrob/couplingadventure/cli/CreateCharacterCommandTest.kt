package com.zegreatrob.couplingadventure.cli

import com.zegreatrob.couplingadventure.engine.Character
import com.zegreatrob.couplingadventure.engine.HeroClass
import com.zegreatrob.couplingadventure.engine.People
import com.zegreatrob.couplingadventure.engine.Player
import com.zegreatrob.minassert.assertIsEqualTo
import com.zegreatrob.testmints.setup
import kotlin.test.Test

class CreateCharacterCommandTest {

    @Test
    fun willUseInputToCreateCharacter() = setup(object : CreateCharacterCommandDispatcher {
        val givenName = "Tim"
        val givenPeople = "Human"
        val givenHeroClass = "Mage"
    }) exercise {
        CreateCharacterCommand(givenName, givenPeople, givenHeroClass)
                .perform()
    } verify { result ->
        result.assertIsEqualTo(
                GameSetupState(listOf(Character(
                        Player("Tim"),
                        People.Human,
                        HeroClass.Mage,
                        HeroClass.Mage.characterSheet
                )))
        )
    }

}