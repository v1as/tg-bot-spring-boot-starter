package ru.v1as.tg.starter.configuration

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import ru.v1as.tg.starter.TgBotProperties
import ru.v1as.tg.starter.TgSender
import ru.v1as.tg.starter.keyboard.StartCommandLinkFactory
import ru.v1as.tg.starter.update.UpdateDataExtractor
import ru.v1as.tg.starter.update.command.BaseCommandsHandler
import ru.v1as.tg.starter.update.command.CommandHandler
import ru.v1as.tg.starter.update.exception.BaseUpdateProcessorExceptionHandler
import ru.v1as.tg.starter.update.exception.UpdateProcessorExceptionHandler

@Configuration
class TgCommandsConfiguration {

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    @ConditionalOnMissingBean
    fun updateProcessorExceptionHandler(
        tgSender: TgSender, updateDataExtractor: UpdateDataExtractor
    ): UpdateProcessorExceptionHandler = BaseUpdateProcessorExceptionHandler(tgSender, updateDataExtractor)

    @Bean
    fun baseCommandsHandler(commandHandlers: List<CommandHandler>) =
        BaseCommandsHandler(commandHandlers)

    @Bean
    fun startCommandLink(tgBotProperties: TgBotProperties) = StartCommandLinkFactory(tgBotProperties)
}