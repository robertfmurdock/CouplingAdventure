package com.zegreatrob.couplingadventure.cli

import com.zegreatrob.couplingadventure.engine.Character

sealed class GameState

data class GameSetupState(val players: List<Character> = emptyList()) : GameState()

data class AdventureGameState(
        val player1: Character,
        val player2: Character,
        val adventureState: AdventureState
) : GameState()

data class AdventureState(val adventureId: String, val locationId: String)