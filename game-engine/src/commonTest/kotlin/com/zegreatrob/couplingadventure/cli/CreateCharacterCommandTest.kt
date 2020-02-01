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
        val givenName = Player("Tim")
        val givenPeople = People.Human
        val givenHeroClass = HeroClass.Mage
    }) exercise {
        CreateCharacterCommand(givenName, givenPeople, givenHeroClass)
                .perform(GameSetupState())
    } verify { result ->
        result.assertIsEqualTo(
                GameSetupState(listOf(Character(
                        givenName,
                        givenPeople,
                        givenHeroClass,
                        givenHeroClass.characterSheet
                )))
        )
    }

    @Test
    fun whenThereIsAnExistingPartyWillAddNewCharacterToIt() = setup(object : CreateCharacterCommandDispatcher {
        val givenName = Player("Tim")
        val givenPeople = People.Human
        val givenHeroClass = HeroClass.Mage

        val initialState = GameSetupState(listOf(stubCharacter()))
    }) exercise {
        CreateCharacterCommand(givenName, givenPeople, givenHeroClass)
                .perform(initialState)
    } verify { result ->
        result.players
                .assertIsEqualTo(
                        initialState.players +
                                Character(givenName, givenPeople, givenHeroClass, givenHeroClass.characterSheet)
                )
    }

}