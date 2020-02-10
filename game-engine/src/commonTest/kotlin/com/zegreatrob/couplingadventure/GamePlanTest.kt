package com.zegreatrob.couplingadventure

import com.zegreatrob.couplingadventure.cli.GameSetupState
import com.zegreatrob.couplingadventure.cli.stubCharacter
import com.zegreatrob.minassert.assertIsEqualTo
import com.zegreatrob.testmints.setup
import kotlin.test.Test

class GamePlanTest {

    @Test
    fun whenStartingWithEmptyStateWillRequestAddCharacter() = setup(object {
        val state = GameSetupState()
    }) exercise {
        GamePlan.nextTransition(state)
    } verify { result: List<Transition> ->
        result.assertIsEqualTo(listOf(Transition.AddCharacter, Transition.AddCharacter))
    }

    @Test
    fun whenStartingWithOnePlayerWillRequestAddCharacter() = setup(object {
        val state = GameSetupState(players = listOf(stubCharacter()))
    }) exercise {
        GamePlan.nextTransition(state)
    } verify { result ->
        result.assertIsEqualTo(listOf(Transition.AddCharacter))
    }

    @Test
    fun whenThereAreTwoPlayersWillStartAdventure() = setup(object {
        val state = GameSetupState(players = listOf(stubCharacter(), stubCharacter()))
    }) exercise {
        GamePlan.nextTransition(state)
    } verify { result ->
        result.assertIsEqualTo(listOf(Transition.StartAdventure))
    }

}