package ru.v1as.tg.starter.update.callback

import org.telegram.telegrambots.meta.api.objects.Update
import ru.v1as.tg.starter.model.TgChat
import ru.v1as.tg.starter.model.TgUser
import ru.v1as.tg.starter.model.base.TgChatWrapper
import ru.v1as.tg.starter.model.base.TgUserWrapper

class CallbackRequest(val update: Update) {
    val data: String = update.callbackQuery.data
    val from: TgUser = TgUserWrapper(update.callbackQuery.from)
    val chat: TgChat = TgChatWrapper(update.callbackQuery.message.chat)
}