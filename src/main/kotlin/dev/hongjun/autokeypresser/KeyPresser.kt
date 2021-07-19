package dev.hongjun.autokeypresser

import java.awt.Robot

class KeyPresser(private val commandStrings: Collection<String>) {
    private val robot: Robot = Robot()
    private val commands: Collection<ICommand> = commandStrings.flatMap {
        parseCommand(it)
    }
    private var tick: Float = 1.0f

    private fun parseTime(ticks: Float): Long = (ticks * tick * 1000.0f).toLong()

    private fun parseCommand(line: String): Collection<ICommand> {
        val trimmed = line.trim()
        if (trimmed.isEmpty()) {
            return listOf()
        }
        val parts = trimmed.split(" ")
        val commandType = parts[0].uppercase()
        val value = parts[1].uppercase()
        return when (commandType) {
            "DELAY" -> listOf(DelayCommand(parseTime(value.toFloat())))
            "PRESS" -> listOf(KeyPressCommand(robot, value[0]))
            "HOLD" -> listOf(KeyHoldCommand(robot, value[0], parseTime(parts[2].toFloat())))
            "TICK" -> {
                tick = value.toFloat()
                listOf()
            }
            else -> throw IllegalStateException()
        }
    }

    suspend fun execute() {
        commands.forEach {
            it.execute()
        }
    }
}
