package com.zegreatrob.couplingadventure

import com.zegreatrob.couplingadventure.cli.AdventureGameState
import com.zegreatrob.couplingadventure.cli.GameSetupState
import com.zegreatrob.couplingadventure.cli.GameState

private const val requiredPlayers = 2

class GamePlan {
    companion object {
        fun nextTransition(state: GameState): List<Transition> = when (state) {
            is GameSetupState -> setupTransitions(state)
            is AdventureGameState -> TODO()
        }

        private fun setupTransitions(state: GameSetupState) = if (state.setupIsComplete()) {
            listOf(Transition.StartAdventure)
        } else {
            generateSequence { Transition.AddCharacter }
                    .take(requiredPlayers - state.players.size)
                    .toList()
        }
    }
}

private fun GameSetupState.setupIsComplete() = players.size == 2
