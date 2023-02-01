package ru.v1as.tg.starter

import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import java.io.Serializable
import java.util.concurrent.CompletableFuture

interface TgSender {

    fun <T : Serializable, Method : BotApiMethod<T>> execute(method: Method): T

    fun <T : Serializable, Method : BotApiMethod<T>> executeAsync(method: Method): CompletableFuture<T>

}