package com.zegreatrob.couplingadventure.cli

interface OutputSyntax {
    fun String.sendToUser() = println(this)
}