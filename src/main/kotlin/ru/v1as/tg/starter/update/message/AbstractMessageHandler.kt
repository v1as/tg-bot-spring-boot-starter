package ru.v1as.tg.starter.update.message

import ru.operation.handler.Handled
import ru.v1as.tg.starter.exceptions.TgBotMethodApiException
import ru.v1as.tg.starter.model.TgChat
import ru.v1as.tg.starter.model.TgUser
import ru.v1as.tg.starter.model.base.TgChatWrapper
import ru.v1as.tg.starter.model.base.TgUserWrapper

abstract class AbstractMessageHandler : MessageHandler {

    override fun handle(input: MessageRequest): Handled {
        val chat = TgChatWrapper(input.message.chat)
        val user = TgUserWrapper(input.message.from)
        return try {
            handle(input, chat, user)
        } catch (ex: TgBotMethodApiException) {
            throw ex
        } catch (e: Exception) {
            error(e)
        }
    }

    abstract fun handle(request: MessageRequest, chat: TgChat, user: TgUser): Handled
}
