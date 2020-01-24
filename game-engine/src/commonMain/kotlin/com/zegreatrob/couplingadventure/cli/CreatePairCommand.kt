package com.zegreatrob.couplingadventure.cli

object CreatePairCommand

interface CreatePairCommandDispatcher : CreateCharacterCommandDispatcher {
    fun CreatePairCommand.perform() {
        CreateCharacterCommand(
                "First, you'll need to identify yourselves. You, on the left... are what is your name?"
        ).perform()
    }
}
