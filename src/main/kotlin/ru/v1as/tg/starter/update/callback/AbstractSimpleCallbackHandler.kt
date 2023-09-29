package ru.v1as.tg.starter.update.callback

import ru.v1as.tg.starter.model.TgChat
import ru.v1as.tg.starter.model.TgUser
import ru.v1as.tg.starter.update.handle.Handled
import ru.v1as.tg.starter.update.handle.error
import ru.v1as.tg.starter.update.handle.handled
import ru.v1as.tg.starter.update.handle.unmatched

abstract class AbstractSimpleCallbackHandler(private val value: String) : CallbackHandler {

    override fun handle(input: CallbackRequest): Handled {
        if (value != input.data) {
            return unmatched()
        }
        return try {
            handle(input.chat, input.from, input)
            handled()
        } catch (e: Exception) {
            error(e)
        }
    }

    abstract fun handle(chat: TgChat, user: TgUser, callbackRequest: CallbackRequest)

}