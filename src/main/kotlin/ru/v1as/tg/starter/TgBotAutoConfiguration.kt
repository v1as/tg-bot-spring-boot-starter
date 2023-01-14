package ru.v1as.tg.starter

import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.telegram.telegrambots.meta.generics.LongPollingBot

@AutoConfiguration
@ConditionalOnProperty("tg.bot")
@EnableConfigurationProperties(TgBotProperties::class)
class TgBotAutoConfiguration {

    @Bean
    fun baseUpdateProcessor() = BaseUpdateProcessor()

    @Bean
    fun longPollingBot(props: TgBotProperties, updateProcessor: TgUpdateProcessor) =
        TgLongPollingBot(props, updateProcessor)

    @Bean
    fun botRunner(tgBot: LongPollingBot) = TgBotRunner(tgBot)

}

