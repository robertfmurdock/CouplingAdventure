package com.zegreatrob.couplingadventure.cli

import com.zegreatrob.couplingadventure.engine.Character
import com.zegreatrob.couplingadventure.engine.HeroClass
import com.zegreatrob.couplingadventure.engine.People
import com.zegreatrob.couplingadventure.engine.Player
import com.zegreatrob.minassert.assertIsEqualTo
import com.zegreatrob.minspy.SpyData
import com.zegreatrob.testmints.setup
import kotlin.test.Test

class CreateCharacterCommandTest {

    @Test
    fun willUseInputToCreateCharacter() = setup(object : CreateCharacterCommandDispatcher {
        override fun InputRequest.ask(): String? = askSpy.spyFunction(message)

        val givenName = "Tim"
        val givenPeople = "Human"
        val givenHeroClass = "Mage"

        val askSpy = SpyData<String?, String>().apply {
            spyReturnValues += listOf(givenName, givenPeople, givenHeroClass)
        }
    }) exercise {
        CreateCharacterCommand("yo")
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