package ru.v1as.tg.starter.update.callback

import ru.v1as.tg.starter.model.TgChat
import ru.v1as.tg.starter.model.TgUser
import ru.v1as.tg.starter.update.handle.Handled
import ru.v1as.tg.starter.update.handle.error
import ru.v1as.tg.starter.update.handle.handled
import ru.v1as.tg.starter.update.handle.unmatched

abstract class AbstractCallbackWithPrefixHandler(val prefix: String) : CallbackHandler {

    override fun handle(input: CallbackRequest): Handled {
        if (!input.data.startsWith(prefix)) {
            return unmatched()
        }
        return try {
            handle(input.data.substring(prefix.length), input.chat, input.from, input)
            handled()
        } catch (e: Exception) {
            error(e)
        }
    }

    abstract fun handle(input: String, chat: TgChat, user: TgUser, callbackRequest: CallbackRequest)
}