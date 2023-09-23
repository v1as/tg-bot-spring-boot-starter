package ru.v1as.tg.starter.update.command

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import ru.v1as.tg.starter.update.message

class CommandRequestTest {
    @Test
    fun `Should parse simple command`() {
        val msg = message("/command1")
        val command = CommandRequest.parse(msg)
        assertEquals(CommandRequest(msg, "command1"), command)
    }

    @Test
    fun `Should parse command with bot name`() {
        val msg = message("/c_1@name")
        val command = CommandRequest.parse(msg)
        assertEquals(CommandRequest(msg, "c_1", "name"), command)
    }

    @Test
    fun `Should not parse not command`() {
        val ex: IllegalArgumentException =
            org.junit.jupiter.api.assertThrows { CommandRequest.parse(message("hi!")) }
        assertEquals("Unsupported command format: hi! ", ex.message)
    }

    @Test
    fun `Should parse command with arguments`() {
        val msg = message("/c1 1   2 3")
        val command = CommandRequest.parse(msg)
        assertEquals(CommandRequest(msg, "c1", "", "1   2 3", listOf("1", "2", "3")), command)
    }

    @Test
    fun `Should parse start command`() {
        val msg = message("/start join_-1001144959646")
        val command = CommandRequest.parse(msg)
        assertEquals(
            CommandRequest(msg, "start", "", "join_-1001144959646", listOf("join", "-1001144959646")),
            command
        )
    }

    @ParameterizedTest
    @CsvSource(
        "/test arg 3,arg,3",
        "/test arg 3 arg2,arg,3",
        "/test arg 3 arg2 4,arg2,4",
    )
    fun `Should find argument`(cmd: String, arg: String, value: String) {
        val parsed = CommandRequest.parse(message(cmd))
        assertEquals(value, parsed.argumentAfter(arg))
    }

    @Test
    fun `Should throw exception if no arg`() {
        var cmd = CommandRequest.parse(message("/test"))
        assertThrows(IllegalStateException::class.java) { cmd.argumentAfter("arg") }

        cmd = CommandRequest.parse(message("/test arg"))
        assertThrows(IllegalStateException::class.java) { cmd.argumentAfter("arg") }
    }
}