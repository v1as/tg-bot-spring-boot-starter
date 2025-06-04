package ru.v1as.tg.starter.update.message

import org.telegram.telegrambots.meta.api.objects.Update
import ru.v1as.tg.starter.update.UpdateHandler
import ru.v1as.tg.starter.update.handle.AbstractListHandler

class BaseMessageUpdateHandler(messageHandlers: List<MessageHandler>) :
    AbstractListHandler<Update, MessageRequest>(messageHandlers), UpdateHandler {

    override fun map(input: Update): MessageRequest? {
        input.message ?: return null
        return MessageRequest(input.message, input)
    }
}