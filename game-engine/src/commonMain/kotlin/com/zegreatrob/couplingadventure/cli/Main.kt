package com.zegreatrob.couplingadventure.cli

fun main() = commandDispatcher {
    MainCommand.perform()
}

fun commandDispatcher(function: MainCommandDispatcher.() -> Unit) {
    val commandDispatcher = object : MainCommandDispatcher {}
    commandDispatcher.function()
}


object MainCommand

interface MainCommandDispatcher : OutputSyntax, InputSyntax {

    fun MainCommand.perform() {
        "Welcome to Coupling Adventure!".sendToUser()

        "First, you'll need to identify yourselves. You, on the left... are what is your name?"
                .sendToUser()

        val name = readLine()
        val people = readLine()
        val heroClass = readLine()

        "Lovely, welcome $name the $people $heroClass!"
                .sendToUser()
    }
}

interface InputSyntax {
    val inputReader: InputReader get() = InputReader
    fun readLine() = inputReader.readLine()
}

expect object InputReader {
    fun readLine(): String?
}

interface OutputSyntax {
    fun String.sendToUser() = println(this)
}
