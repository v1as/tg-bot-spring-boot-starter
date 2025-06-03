package ru.v1as.tg.starter

import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod
import java.io.Serializable
import java.util.concurrent.CompletableFuture

open class TgAbsSender(private val tgClient: OkHttpTelegramClient) : TgSender {

    override fun <T : Serializable, Method : BotApiMethod<T>> execute(method: Method): T {
        return tgClient.execute(method)
    }

    override fun <T : Serializable, Method : BotApiMethod<T>> executeAsync(method: Method): CompletableFuture<T> {
        return tgClient.executeAsync(method)
    }

}