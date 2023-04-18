package ru.v1as.tg.starter.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.v1as.tg.starter.update.command.BaseCommandsHandler
import ru.v1as.tg.starter.update.command.CommandHandler

@Configuration
class TgCommandsConfiguration {

    @Bean
    fun baseCommandsHandler(commandHandlers: List<CommandHandler>) =
        BaseCommandsHandler(commandHandlers)
}