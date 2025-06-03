package ru.v1as.tg.starter

import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import ru.v1as.tg.starter.model.TgChat
import java.io.Serializable
import java.util.concurrent.CompletableFuture

interface TgSender {

    fun <T : Serializable, Method : BotApiMethod<T>> execute(method: Method): T

    fun <T : Serializable, Method : BotApiMethod<T>> executeAsync(method: Method): CompletableFuture<T>

    fun message(chat: TgChat, message: String) {
        execute(SendMessage(chat.idStr(), message))
    }

}