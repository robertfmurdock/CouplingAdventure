package com.zegreatrob.couplingadventure.cli

import com.zegreatrob.minassert.assertIsEqualTo
import com.zegreatrob.testmints.async.setupAsync
import com.zegreatrob.testmints.async.testAsync
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.PipedInputStream
import java.io.PipedOutputStream
import java.io.PrintStream
import kotlin.test.Test

class MainIntegrationTest {

    @Test
    fun willWelcomeUserAndStartAdventure() = testAsync {
        setupAsync(object {
        }) exerciseAsync {
            captureOutput { userOutputStream ->
                with(userOutputStream) {
                    println("exit")
                }
                main()
            }
        } verifyAsync { result ->
            result.filter { it != "" }
                    .take(2)
                    .assertIsEqualTo(listOf(
                            "Welcome to Coupling Adventure!",
                            "First, you'll need to identify yourselves. You, on the left... are what is your name?"
                    ))
        }
    }

    @Test
    fun userWillBeAbleToCreateTheirCharacter() = testAsync {
        setupAsync(object {
        }) exerciseAsync {
            captureOutput { userOutputStream ->
                coroutineScope {
                    launch { main() }

                    with(userOutputStream) {
                        println("RoB")
                        println("Dwarf")
                        println("Mage")
                        close()
                    }
                }
            }
        } verifyAsync { result ->
            result.filterNot { it.isNullOrEmpty() }.takeLast(2)
                    .assertIsEqualTo(listOf(
                            "Lovely, welcome RoB the Dwarf Mage!",
                            "Looks like that's the end!"
                    ))
        }
    }

}

private suspend fun captureOutput(work: suspend (PrintStream) -> Unit): List<String> {
    val byteArrayOutputStream = ByteArrayOutputStream()
    val out = System.out

    val userOutputStream = setupUserOutputStream()
    try {
        System.setOut(PrintStream(byteArrayOutputStream))
        work(userOutputStream)
    } finally {
        System.setOut(out)
    }
    return withContext(Dispatchers.IO) { byteArrayOutputStream.toString("UTF-8")?.lines()!! }
}

private fun setupUserOutputStream(): PrintStream {
    val inputStream = PipedInputStream()
    val userOutputStream = PrintStream(PipedOutputStream(inputStream))
    System.setIn(inputStream)
    return userOutputStream
}