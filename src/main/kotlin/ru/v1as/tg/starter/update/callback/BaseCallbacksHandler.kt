package ru.v1as.tg.starter.update.callback

import mu.KLoggable
import org.telegram.telegrambots.meta.api.objects.Update
import ru.operation.handler.Handled
import ru.operation.handler.impl.list.HandlerList
import ru.v1as.tg.starter.update.UpdateHandler

class BaseCallbacksHandler(callbackHandlers: List<CallbackHandler>) : UpdateHandler, KLoggable {

    override val logger = logger()

    private val callbackHandlers = HandlerList(callbackHandlers)

    override fun handle(input: Update): Handled {
        return callbackHandlers.handle(map(input))
    }

    fun map(input: Update): CallbackRequest? {
        input.callbackQuery ?: return null
        val callback = CallbackRequest(input)
        logger.debug {
            "Callback parsed: '${callback.data}' " +
                    "from ${callback.from.usernameOrFullName()} " +
                    "in ${callback.chat.getId()}"
        }
        return callback
    }
}