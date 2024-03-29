package ru.v1as.tg.starter.update.command

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import ru.v1as.tg.starter.update.messageUpdate

class CommandRequestTest {
    @Test
    fun `Should parse simple command`() {
        val msg = messageUpdate("/command1")
        val command = CommandRequest.parse(msg)
        assertEquals(CommandRequest(msg, "command1"), command)
    }

    @Test
    fun `Should parse command with bot name`() {
        val msg = messageUpdate("/c_1@name")
        val command = CommandRequest.parse(msg)
        assertEquals(CommandRequest(msg, "c_1", "name"), command)
    }

    @Test
    fun `Should not parse not command`() {
        val ex: IllegalArgumentException =
            org.junit.jupiter.api.assertThrows { CommandRequest.parse(messageUpdate("hi!")) }
        assertEquals("Unsupported command format: hi! ", ex.message)
    }

    @Test
    fun `Should parse command with arguments`() {
        val msg = messageUpdate("/c1 1   2 3")
        val command = CommandRequest.parse(msg)
        assertEquals(CommandRequest(msg, "c1", "", "1   2 3", listOf("1", "2", "3")), command)
    }

    @Test
    fun `Should parse start command`() {
        val msg = messageUpdate("/start join_-1001144959646")
        val command = CommandRequest.parse(msg)
        assertEquals(
            CommandRequest(
                msg,
                "start",
                "",
                "join_-1001144959646",
                listOf("join", "-1001144959646")
            ),
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
        val parsed = CommandRequest.parse(messageUpdate(cmd))
        assertEquals(value, parsed.argumentAfter(arg))
    }

    @Test
    fun `Should parse new line`() {
        val parsed = CommandRequest.parse(messageUpdate("/test arg\n3"))
        assertEquals("3", parsed.argumentAfter("arg"))
    }

    @Test
    fun `Should throw exception if no arg`() {
        var cmd = CommandRequest.parse(messageUpdate("/test"))
        assertThrows(IllegalStateException::class.java) { cmd.argumentAfter("arg") }

        cmd = CommandRequest.parse(messageUpdate("/test arg"))
        assertThrows(IllegalStateException::class.java) { cmd.argumentAfter("arg") }
    }
}