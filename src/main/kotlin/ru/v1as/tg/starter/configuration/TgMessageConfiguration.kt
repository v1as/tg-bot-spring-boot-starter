package ru.v1as.tg.starter.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.v1as.tg.starter.update.message.BaseMessageUpdateHandler
import ru.v1as.tg.starter.update.message.MessageHandler

@Configuration
class TgMessageConfiguration {
    @Bean
    fun baseMessageHandler(messageHandlers: List<MessageHandler>) = BaseMessageUpdateHandler(messageHandlers)
}