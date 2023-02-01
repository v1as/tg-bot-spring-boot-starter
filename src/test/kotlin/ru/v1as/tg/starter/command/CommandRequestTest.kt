package ru.v1as.tg.starter.command

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.telegram.telegrambots.meta.api.objects.Message


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
            assertThrows { CommandRequest.parse(message("hi!")) }
        assertEquals("Unsupported command format: hi! ", ex.message)
    }

    @Test
    fun `Should parse command with arguments`() {
        val msg = message("/c1 1   2 3")
        val command = CommandRequest.parse(msg)
        assertEquals(CommandRequest(msg, "c1", "", listOf("1", "2", "3")), command)
    }
}

private fun message(text: String): Message {
    val msg: Message = mock(Message::class.java)
    `when`(msg.text).thenReturn(text)
    return msg
}