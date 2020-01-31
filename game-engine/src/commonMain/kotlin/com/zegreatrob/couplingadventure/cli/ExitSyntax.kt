package com.zegreatrob.couplingadventure.cli

interface ExitSyntax {

    var exitRequested: Boolean

    fun requestExit() {
        exitRequested = true
    }

}
