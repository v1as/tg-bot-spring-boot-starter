package ru.v1as.tg.starter.update.callback

import org.telegram.telegrambots.meta.api.objects.Update
import ru.v1as.tg.starter.model.base.TgChatWrapper
import ru.v1as.tg.starter.model.base.TgUserWrapper
import ru.v1as.tg.starter.update.UpdateRequest

class CallbackRequest(update: Update) : UpdateRequest(
    update,
    TgUserWrapper(update.callbackQuery.from),
    TgChatWrapper(update.callbackQuery.message.chat)
) {
    val data: String = update.callbackQuery.data
    fun callbackQueryId() = update.callbackQuery.id
}