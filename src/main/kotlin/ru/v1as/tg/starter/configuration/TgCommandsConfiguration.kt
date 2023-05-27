package ru.v1as.tg.starter.configuration

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.v1as.tg.starter.TgBotProperties
import ru.v1as.tg.starter.TgSender
import ru.v1as.tg.starter.keyboard.StartCommandLinkFactory
import ru.v1as.tg.starter.update.BaseUpdateProcessorExceptionHandler
import ru.v1as.tg.starter.update.UpdateDataExtractor
import ru.v1as.tg.starter.update.UpdateProcessorExceptionHandler
import ru.v1as.tg.starter.update.command.BaseCommandsHandler
import ru.v1as.tg.starter.update.command.CommandHandler

@Configuration
class TgCommandsConfiguration {

    @Bean
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