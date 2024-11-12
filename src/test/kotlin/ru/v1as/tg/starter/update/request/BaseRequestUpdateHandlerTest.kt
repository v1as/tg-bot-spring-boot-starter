package ru.v1as.tg.starter.update.request

import java.time.Duration.ofSeconds
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import ru.v1as.tg.starter.update.BaseUpdateDataExtractor
import ru.v1as.tg.starter.update.messageUpdate

class BaseRequestUpdateHandlerTest {

    val handler: BaseRequestUpdateHandler = BaseRequestUpdateHandler(BaseUpdateDataExtractor())

    @Test
    fun `Should remove one off request`() {
        var mathed = false
        handler.register(UpdateRequest({ mathed = true }, ofSeconds(3)))
        assertTrue(handler.handle(messageUpdate()).isHandled)
        assertTrue(mathed)

        mathed = false
        assertFalse(handler.handle(messageUpdate()).isHandled)
        assertFalse(mathed)
    }

    @Test
    fun `Should match on exception of matching`() {
        var mathed = false
        handler.register(
            UpdateRequest(
                {
                    mathed = true
                    throw RuntimeException()
                },
                ofSeconds(3),
            )
        )
        assertTrue(handler.handle(messageUpdate()).isError)
        assertTrue(mathed)

        mathed = false
        assertFalse(handler.handle(messageUpdate()).isHandled)
        assertFalse(mathed)
    }

    @Test
    fun `Canceled should not match`() {
        var mathed = false
        val request = UpdateRequest({ mathed = true }, ofSeconds(3))
        handler.register(request)
        request.cancel()
        assertFalse(handler.handle(messageUpdate()).isHandled)
        assertFalse(mathed)
    }

    @Test
    fun `Should not match on filtering exception`() {
        var mathed = false
        val request =
            UpdateRequest({ mathed = true }, ofSeconds(3), filter = { throw RuntimeException() })
        handler.register(request)
        assertFalse(handler.handle(messageUpdate()).isHandled)
        assertFalse(mathed)
    }

    @Test
    fun `Should match userId and chatId`() {
        var mathed = false
        handler.register(UpdateRequest({ mathed = true }, ofSeconds(3), 1, 1))
        assertTrue(handler.handle(messageUpdate(chatId = 1, userId = 1)).isHandled)
        assertTrue(mathed)
    }

    @Test
    fun `Should not match wrong userId`() {
        var mathed = false
        handler.register(UpdateRequest({ mathed = true }, ofSeconds(3), 2, 1))
        assertFalse(handler.handle(messageUpdate(chatId = 1, userId = 1)).isHandled)
        assertFalse(mathed)
    }

    @Test
    fun `Should not match wrong chatId`() {
        var mathed = false
        handler.register(UpdateRequest({ mathed = true }, ofSeconds(3), 1, 2))
        assertFalse(handler.handle(messageUpdate(chatId = 1, userId = 1)).isHandled)
        assertFalse(mathed)
    }
}
