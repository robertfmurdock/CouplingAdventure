package com.zegreatrob.couplingadventure.cli

data class InputRequest(val message: String? = null, val options: List<String> = emptyList())

interface InputRequestSyntax : InputSyntax, OutputSyntax, ExitSyntax {
    fun InputRequest.ask(): String? {

        message?.sendToUser()

        options.sendNumberedToUser()

        return options.readValidResponse()
    }

    private fun List<String>.readValidResponse() = when {
        exitRequested -> ""
        else -> readLine()
                .also { if (it == "exit") requestExit() }
                .let(substituteValueForIndex(this))
    }

    private fun List<String>.sendNumberedToUser() = forEachIndexed { index, option ->
        "${index + 1}. $option".sendToUser()
    }

    fun substituteValueForIndex(options: List<String>): (String?) -> String? {
        val mapIndexed = options.mapIndexed { index, option -> "${index + 1}" to option }.toMap()

        return { line: String? ->
            val selectedValue = mapIndexed[line?.removeSuffix(".")]
            when {
                selectedValue != null -> selectedValue
                options.isNotEmpty() && !options.contains(line) -> {
                    "I'm afraid I misheard you. Could you run that by me again?".sendToUser()
                    options.sendNumberedToUser()
                    options.readValidResponse()
                }
                else -> line
            }
        }
    }

    fun waitForResponse() = InputRequest(null).ask()
    fun String?.waitForResponse() = InputRequest(this).ask()
}
