package com.zegreatrob.couplingadventure.cli

data class InputRequest(val message: String? = null)

interface InputRequestSyntax : InputSyntax, OutputSyntax {
    fun InputRequest.ask() = message?.sendToUser().let { readLine() }

    fun waitForResponse() = InputRequest(null).ask()
    fun String?.waitForResponse() = InputRequest(this).ask()
}
