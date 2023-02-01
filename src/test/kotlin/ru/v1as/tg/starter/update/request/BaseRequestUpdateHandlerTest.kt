package ru.v1as.tg.starter.update.request

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import ru.v1as.tg.starter.update.BaseUpdateDataExtractor
import ru.v1as.tg.starter.update.messageUpdate
import java.time.Duration.ofSeconds

class BaseRequestUpdateHandlerTest {

    val handler: BaseRequestUpdateHandler = BaseRequestUpdateHandler(BaseUpdateDataExtractor())

    @Test
    fun `Should remove one off request`() {
        var mathed = false
        handler.register(UpdateRequest({ mathed = true; true }, ofSeconds(3)))
        assertTrue(handler.handle(messageUpdate()).isDone())
        assertTrue(mathed)

        mathed = false
        assertFalse(handler.handle(messageUpdate()).isDone())
        assertFalse(mathed)
    }

    @Test
    fun `Should not remove request`() {
        var mathed = false
        handler.register(UpdateRequest({ mathed = true; false }, ofSeconds(3)))
        assertTrue(handler.handle(messageUpdate()).isDone())
        assertTrue(mathed)

        mathed = false
        assertTrue(handler.handle(messageUpdate()).isDone())
        assertTrue(mathed)
    }

    @Test
    fun `Should match userId and chatId`() {
        var mathed = false
        handler.register(UpdateRequest({ mathed = true; true }, ofSeconds(3), 1, 1))
        assertTrue(handler.handle(messageUpdate(chatId = 1, userId = 1)).isDone())
        assertTrue(mathed)
    }

    @Test
    fun `Should not match wrong userId`() {
        var mathed = false
        handler.register(UpdateRequest({ mathed = true; true }, ofSeconds(3), 2, 1))
        assertFalse(handler.handle(messageUpdate(chatId = 1, userId = 1)).isDone())
        assertFalse(mathed)
    }

    @Test
    fun `Should not match wrong chatId`() {
        var mathed = false
        handler.register(UpdateRequest({ mathed = true; true }, ofSeconds(3), 1, 2))
        assertFalse(handler.handle(messageUpdate(chatId = 1, userId = 1)).isDone())
        assertFalse(mathed)
    }
}