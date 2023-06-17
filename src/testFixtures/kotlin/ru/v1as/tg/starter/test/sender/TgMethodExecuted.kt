package ru.v1as.tg.starter.test.sender

import java.io.Serializable
import org.telegram.telegrambots.meta.api.methods.BotApiMethod

data class TgMethodExecuted<T : Serializable, Method : BotApiMethod<T>>(
    val method: Method,
    val result: T
)
