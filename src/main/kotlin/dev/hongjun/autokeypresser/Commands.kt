package dev.hongjun.autokeypresser

import kotlinx.coroutines.channels.ticker
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.awt.Robot
import java.awt.event.KeyEvent

sealed interface ICommand {
    suspend fun execute()
}

class DelayCommand(private val delayTime: Long) : ICommand {
    override suspend fun execute() {
        delay(delayTime)
    }
}

class KeyPressCommand(private val robot: Robot, private val key: Char) : ICommand {
    override suspend fun execute() {
        robot.keyPress(key.code)
        robot.keyRelease(key.code)
    }
}

class KeyHoldCommand(private val robot: Robot, private val key: Char, private val delayTime: Long) : ICommand {
    override suspend fun execute() {
        robot.keyPress(key.code)
        delay(delayTime)
        robot.keyRelease(key.code)
    }
}
