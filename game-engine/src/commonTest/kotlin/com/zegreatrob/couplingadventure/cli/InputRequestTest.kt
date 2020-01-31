package com.zegreatrob.couplingadventure.cli

import com.zegreatrob.minassert.assertIsEqualTo
import com.zegreatrob.testmints.setup
import kotlin.test.Test

class InputRequestTest {

    class GivenMultipleChoice {

        abstract class GivenMultipleChoiceScenario : InputRequestSyntax {
            override var exitRequested = false

            open val input: List<String> = emptyList()

            private val inputIterator by lazy { input.iterator() }

            override fun readLine() = if (inputIterator.hasNext()) inputIterator.next() else ""

            val request = InputRequest("Do as I command!", listOf("Yep", "Nah", "..."))

            val output = mutableListOf<String>()
            override fun String.sendToUser() {
                output += this
            }

        }

        @Test
        fun willPresentChoices() = setup(object : GivenMultipleChoiceScenario() {
            override var exitRequested = true
        }) exercise {
            request.ask()
        } verify {
            output.assertIsEqualTo(
                    listOf(
                            "Do as I command!",
                            "1. Yep",
                            "2. Nah",
                            "3. ..."
                    )
            )
        }

        @Test
        fun willAcceptTheNumberAndUseTheValue() = setup(object : GivenMultipleChoiceScenario() {
            override val input = listOf("2")
        }) exercise {
            request.ask()
        } verify { result ->
            result.assertIsEqualTo("Nah")
        }

        @Test
        fun willAcceptTheNumberWhenFollowedByADotAsWellAndUseTheValue() = setup(object : GivenMultipleChoiceScenario() {
            override val input = listOf("2.")
        }) exercise {
            request.ask()
        } verify { result ->
            result.assertIsEqualTo("Nah")
        }

        @Test
        fun willAcceptTheValue() = setup(object : GivenMultipleChoiceScenario() {
            override val input = listOf("...")
        }) exercise {
            request.ask()
        } verify { result ->
            result.assertIsEqualTo("...")
        }

        @Test
        fun willPromptWithOptionsAgainWhenGivenResponseThatIsNotAllowed() = setup(object : GivenMultipleChoiceScenario() {
            override val input = listOf("Say again?", "Yep")
        }) exercise {
            request.ask()
        } verify {
            output.assertIsEqualTo(
                    listOf(
                            "Do as I command!",
                            "1. Yep",
                            "2. Nah",
                            "3. ...",
                            "I'm afraid I misheard you. Could you run that by me again?",
                            "1. Yep",
                            "2. Nah",
                            "3. ..."
                    )
            )

        }

    }


}