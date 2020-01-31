package com.zegreatrob.couplingadventure.cli

actual object InputReader {
    actual fun readLine(): String? {
        var result: String? = null
        while (result.isNullOrEmpty()) {
            result = kotlin.io.readLine()
            Thread.sleep(30)
        }
        return result
    }
}