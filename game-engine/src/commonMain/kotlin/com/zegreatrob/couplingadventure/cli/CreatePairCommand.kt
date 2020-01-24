package com.zegreatrob.couplingadventure.cli

object CreatePairCommand

interface CreatePairCommandDispatcher : CreateCharacterCommandDispatcher, InputRequestSyntax {
    fun CreatePairCommand.perform() {
        val name = "First, you'll need to identify yourselves. You, on the left... are what is your name?"
                .waitForResponse()
        val people = waitForResponse()
        val heroClass = waitForResponse()

        "Lovely, welcome $name the $people $heroClass!".sendToUser()
        if (name != null && people != null && heroClass != null) {
            CreateCharacterCommand(name, people, heroClass).perform()
        }
    }
}
