package com.zegreatrob.couplingadventure.cli

import com.zegreatrob.couplingadventure.engine.Character

sealed class GameState

data class GameSetupState(val players: List<Character> = emptyList()) : GameState()
