package com.zegreatrob.couplingadventure.cli

import com.zegreatrob.minassert.assertIsEqualTo
import com.zegreatrob.testmints.async.setupAsync
import com.zegreatrob.testmints.async.testAsync
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import kotlin.test.Test

class MainIntegrationTest {

    @Test
    fun willWelcomeUser() = testAsync {
        setupAsync(object {
        }) exerciseAsync {
            captureOutput { main() }
        } verifyAsync { result ->
            result?.filter { it != "" }
                    .assertIsEqualTo(listOf("Welcome to Coupling Adventure!"))
        }
    }

}

private fun captureOutput(work: () -> Unit): List<String>? {
    val byteArrayOutputStream = ByteArrayOutputStream()
    val out = System.out
    try {
        System.setOut(PrintStream(byteArrayOutputStream))
        work()
    } finally {
        System.setOut(out)
    }
    return byteArrayOutputStream.toString("UTF-8")?.lines()
}