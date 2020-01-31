package com.zegreatrob.couplingadventure.cli

data class CLIGameRunner(val cliGamePlan: List<(GameState) -> CLICommandBuilder?>)

interface CLIGameRunnerPerformer : InputRequestSyntax, ExitSyntax {

    fun CLIGameRunner.perform(): GameState {
        return runNextStep(GameSetupState(), cliGamePlan)
    }

    private fun runNextStep(gameState: GameState, cliGamePlan: List<(GameState) -> CLICommandBuilder?>): GameState {
        val builder = findNextCommandBuilder(cliGamePlan, gameState)
        return performCLICommand(builder, cliGamePlan, gameState)
    }

    private fun findNextCommandBuilder(cliGamePlan: List<(GameState) -> CLICommandBuilder?>, gameState: GameState) =
            cliGamePlan.fold<(GameState) -> CLICommandBuilder?, CLICommandBuilder?>(null) { builder, function ->
                builder ?: function(gameState)
            }

    private fun performCLICommand(
            commandBuilder: CLICommandBuilder?,
            gamePlan: List<(GameState) -> CLICommandBuilder?>,
            gameState: GameState
    ) = if (commandBuilder == null || exitRequested) {
        "Looks like that's the end!".sendToUser()
        gameState
    } else {
        val newGameState = (commandBuilder.presentUserPromptsAndRunCommand() ?: gameState)
        runNextStep(newGameState, gamePlan)
    }


    private fun CLICommandBuilder.presentUserPromptsAndRunCommand(): GameSetupState? {
        val responses = inputRequests.mapIndexed { index, s ->

            if (exitRequested) {
                return null
            }

            index to s.ask()
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
        val inputRequests: List<InputRequest>,
        val commandFunction: (inputs: List<String>) -> GameSetupState
)
