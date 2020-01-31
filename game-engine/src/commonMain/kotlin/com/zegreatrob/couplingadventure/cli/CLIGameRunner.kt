package com.zegreatrob.couplingadventure.cli

data class CLIGameRunner(val cliGamePlan: List<(GameState) -> CLICommandBuilder?>)

interface CLIGameRunnerPerformer : InputRequestSyntax {

    fun CLIGameRunner.perform(): GameSetupState {
        val nextStep = cliGamePlan.firstOrNull()

        if (nextStep == null) {
            "Looks like that's the end!".sendToUser()
        }

        return nextStep
                ?.invoke(GameSetupState())
                ?.presentUserPromptsAndRunCommand()
                ?: GameSetupState()
    }

    private fun CLICommandBuilder.presentUserPromptsAndRunCommand(): GameSetupState? {
        val responses = inputRequests.mapIndexed { index, s ->

            if(exitRequested) {
                return null
            }

            index to s.waitForResponse()
        }.toMap()
        val nonNullValues = responses.values.filterNotNull()

        return if (nonNullValues.size == responses.values.size) {
            commandFunction(nonNullValues)
        } else {
            null
        }
    }

}

data class CLICommandBuilder(
        val inputRequests: List<String?>,
        val commandFunction: (inputs: List<String>) -> GameSetupState
)
