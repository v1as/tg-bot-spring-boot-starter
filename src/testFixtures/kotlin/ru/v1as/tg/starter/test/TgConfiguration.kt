package ru.v1as.tg.starter.test

import java.io.Serializable
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import ru.v1as.tg.starter.test.sender.SendMessageMethodHandler
import ru.v1as.tg.starter.test.sender.TgSenderMethodHandler
import ru.v1as.tg.starter.test.sender.TgSenderTestable

@TestConfiguration
class TgConfiguration {

    @Bean fun sendMessageMethodHandler() = SendMessageMethodHandler()

    @Bean
    fun tgSenderTestable(
        methodHandlers:
            List<TgSenderMethodHandler<out Serializable, out BotApiMethod<out Serializable>>>
    ): TgSenderTestable = TgSenderTestable(methodHandlers)
}
