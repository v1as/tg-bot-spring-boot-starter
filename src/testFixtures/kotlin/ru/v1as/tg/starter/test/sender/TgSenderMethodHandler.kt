package ru.v1as.tg.starter.test.sender

import java.io.Serializable
import org.telegram.telegrambots.meta.api.methods.BotApiMethod

interface TgSenderMethodHandler<T : Serializable, Method : BotApiMethod<T>> {

    fun execute(method: Method): T
}
