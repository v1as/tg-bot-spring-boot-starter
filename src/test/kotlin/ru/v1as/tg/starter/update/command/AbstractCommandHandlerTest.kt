package ru.v1as.tg.starter.update.command

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import ru.v1as.tg.starter.model.base.TgChatWrapper
import ru.v1as.tg.starter.model.base.TgUserWrapper

class AbstractCommandHandlerTest {

    @Test
    fun `Should return command name if description is empty`() {
        val commandName = "test"
        val description = object : AbstractCommandHandler(commandName, "") {
            override fun handle(
                command: CommandRequest,
                user: TgUserWrapper,
                chat: TgChatWrapper
            ) {
            }
        }.description()
        assertEquals(commandName, description)
    }
}