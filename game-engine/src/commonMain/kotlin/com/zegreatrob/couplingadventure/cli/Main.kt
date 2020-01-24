package com.zegreatrob.couplingadventure.cli

fun main() = commandDispatcher {
    MainCommand.perform()
}

fun commandDispatcher(function: MainCommandDispatcher.() -> Unit) {
    val commandDispatcher = object : MainCommandDispatcher {}
    commandDispatcher.function()
}

object MainCommand

interface MainCommandDispatcher : OutputSyntax, InputRequestSyntax, CreatePairCommandDispatcher {

    fun MainCommand.perform() {
        "Welcome to Coupling Adventure!"
                .sendToUser()
        CreatePairCommand.perform()
    }

}
