package ru.v1as.tg.starter.update.message

import mu.KLogging
import org.telegram.telegrambots.meta.api.objects.Update
import ru.operation.handler.impl.list.HandlerList
import ru.v1as.tg.starter.update.UpdateHandler

class BaseMessageUpdateHandler(messageHandlers: List<MessageHandler>) : UpdateHandler {
    companion object : KLogging()

    private val handlers = HandlerList(messageHandlers)

    fun map(input: Update): MessageRequest? {
        input.message ?: return null
        val req = MessageRequest(input)
        logger.debug {
            "Message received: '${input.message}' " +
                "from ${req.from.usernameOrFullName()} " +
                "in ${req.chat.getId()}"
        }
        return req
    }

    override fun handle(update: Update) = handlers.handle(map(update))
}
