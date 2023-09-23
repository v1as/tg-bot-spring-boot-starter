package ru.v1as.tg.starter.update.callback

import org.telegram.telegrambots.meta.api.objects.Update
import ru.v1as.tg.starter.update.UpdateHandler
import ru.v1as.tg.starter.update.handle.AbstractListHandler

class BaseCallbacksHandler(callbackHandlers: List<CallbackHandler>) :
    AbstractListHandler<Update, CallbackRequest>(callbackHandlers), UpdateHandler {
    override fun map(input: Update): CallbackRequest? {
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