package ru.v1as.tg.starter.update

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MethodsKtTest {
    @Test
    fun shouldCreateEditMessage() {
        val edit = editMessageText {
            text = "some text"
            messageId = 123
        }
        assertEquals("some text", edit.text)
        assertEquals(123, edit.messageId)
    }
}