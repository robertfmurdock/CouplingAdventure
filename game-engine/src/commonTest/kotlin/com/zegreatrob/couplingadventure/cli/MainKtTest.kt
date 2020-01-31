package com.zegreatrob.couplingadventure.cli

import com.zegreatrob.testmints.setup
import kotlin.test.Test

class MainKtTest {

    @Test
    fun willStartByWelcomingToGameAndPresentingCharacterCreator() = setup(object : MainCommandDispatcher {
        override var exitRequested: Boolean = false

    }) exercise {

    } verify { result ->

    }


}