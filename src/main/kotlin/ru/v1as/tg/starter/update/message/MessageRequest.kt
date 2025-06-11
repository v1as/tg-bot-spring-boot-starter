package ru.v1as.tg.starter.update.message

import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.message.Message
import ru.v1as.tg.starter.model.base.TgChatWrapper
import ru.v1as.tg.starter.model.base.TgUserWrapper
import ru.v1as.tg.starter.update.UpdateRequest

class MessageRequest(update: Update) :
    UpdateRequest(update, TgUserWrapper(update.message.from), TgChatWrapper(update.message.chat)) {
    val message: Message = update.message
}
