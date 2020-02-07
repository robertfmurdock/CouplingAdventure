package com.zegreatrob.couplingadventure

import com.zegreatrob.couplingadventure.cli.AdventureGameState
import com.zegreatrob.couplingadventure.cli.GameSetupState
import com.zegreatrob.couplingadventure.cli.GameState

class GamePlan {
    companion object {
        fun nextTransition(state: GameState) = when (state) {
            is GameSetupState -> if (state.setupIsComplete()) {
                Transition.StartAdventure
            } else {
                Transition.Setup
            }
            is AdventureGameState -> TODO()
        }
    }
}

private fun GameSetupState.setupIsComplete() = players.size == 2
