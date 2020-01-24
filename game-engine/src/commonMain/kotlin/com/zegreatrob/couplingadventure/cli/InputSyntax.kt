package com.zegreatrob.couplingadventure.cli

interface InputSyntax {
    val inputReader: InputReader get() = InputReader
    fun readLine() = InputReader.readLine()
}