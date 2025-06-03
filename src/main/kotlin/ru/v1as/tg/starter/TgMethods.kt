package ru.v1as.tg.starter

import org.telegram.telegrambots.meta.api.objects.message.Message
import ru.v1as.tg.starter.model.TgChat
import ru.v1as.tg.starter.model.TgUser
import java.util.concurrent.CompletableFuture

interface TgMethods {
    fun message(user: TgUser, message: String): Message

    fun message(chat: TgChat, message: String): CompletableFuture<Message>
}