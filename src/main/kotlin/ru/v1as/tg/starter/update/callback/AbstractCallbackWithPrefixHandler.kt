package ru.v1as.tg.starter.update.callback

import ru.operation.handler.AbstractHandler
import ru.operation.handler.Handled
import ru.operation.handler.Handled.handled
import ru.v1as.tg.starter.model.TgChat
import ru.v1as.tg.starter.model.TgUser

abstract class AbstractCallbackWithPrefixHandler(val prefix: String) : AbstractHandler<CallbackRequest>(),
    CallbackHandler {

    override fun check(input: CallbackRequest?): Boolean {
        return input?.data?.startsWith(prefix) == true
    }

    override fun handleInternal(input: CallbackRequest): Handled {
        handle(input.data.substring(prefix.length), input.chat, input.from, input)
        return handled()
    }

    abstract fun handle(input: String, chat: TgChat, user: TgUser, callbackRequest: CallbackRequest)
}