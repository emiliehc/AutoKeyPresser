package dev.hongjun.autokeypresser

import java.awt.*
import kotlinx.coroutines.*
import java.io.File
import kotlin.system.exitProcess

fun main(args: Array<String>) = runBlocking {
    if (args.isEmpty()) {
        System.err.println("Please ensure that the input script is passed as a command line argument")
        exitProcess(1)
    }
    args.forEachIndexed { index, filePath ->
        launch {
            val file = File(filePath)
            if (!file.exists()) {
                System.err.println("File $filePath is invalid")
                return@launch
            }
            val lines = file.readLines()
            val keyPresser = KeyPresser(lines)
            keyPresser.execute()
        }
    }
}
