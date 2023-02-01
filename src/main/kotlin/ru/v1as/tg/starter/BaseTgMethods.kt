package ru.v1as.tg.starter

import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import ru.v1as.tg.starter.model.TgChat
import ru.v1as.tg.starter.model.TgUser

class BaseTgMethods(private val sender: TgSender) : TgMethods {

    override fun message(user: TgUser, message: String) =
        sender.execute(SendMessage(user.idStr(), message))

    override fun message(chat: TgChat, message: String) =
        sender.executeAsync(SendMessage(chat.idStr(), message))
}