package com.zegreatrob.couplingadventure.cli

data class InputRequest(val message: String? = null)

interface InputRequestSyntax : InputSyntax, OutputSyntax, ExitSyntax {
    fun InputRequest.ask() = message?.sendToUser().let {
        readLine()
                .also { if (it == "exit") requestExit() }
    }

    fun waitForResponse() = InputRequest(null).ask()
    fun String?.waitForResponse() = InputRequest(this).ask()
}
