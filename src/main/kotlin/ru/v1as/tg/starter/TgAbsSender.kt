package ru.v1as.tg.starter

import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.bots.AbsSender
import java.io.Serializable
import java.util.concurrent.CompletableFuture

open class TgAbsSender(private val absSender: AbsSender) : TgSender {

    override fun <T : Serializable, Method : BotApiMethod<T>> execute(method: Method): T {
        return absSender.execute(method)
    }

    override fun <T : Serializable, Method : BotApiMethod<T>> executeAsync(method: Method): CompletableFuture<T> {
        return absSender.executeAsync(method)
    }

}