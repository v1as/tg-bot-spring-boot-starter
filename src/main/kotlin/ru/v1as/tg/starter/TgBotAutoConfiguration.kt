package ru.v1as.tg.starter

import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.telegram.telegrambots.meta.bots.AbsSender
import org.telegram.telegrambots.meta.generics.LongPollingBot
import ru.v1as.tg.starter.update.*

@AutoConfiguration
@ConditionalOnProperty("tg.bot")
@EnableConfigurationProperties(TgBotProperties::class)
class TgBotAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    fun unmatchedUpdateHandlerStub() = UnmatchedUpdateHandler { }

    @Bean
    fun baseUpdateProcessor(
        beforeUpdateHandlers: List<BeforeUpdateHandler>,
        updateHandlers: List<UpdateHandler>,
        unmatchedUpdateHandler: UnmatchedUpdateHandler,
        afterUpdateHandlers: List<AfterUpdateHandler>,
    ) =
        BaseUpdateProcessor(
            beforeUpdateHandlers,
            updateHandlers,
            unmatchedUpdateHandler,
            afterUpdateHandlers
        )

    @Bean
    fun longPollingBot(props: TgBotProperties, updateProcessor: UpdateProcessor) =
        TgLongPollingBot(props, updateProcessor)

    @Bean
    fun botRunner(tgBot: LongPollingBot) = TgBotRunner(tgBot)

    @Bean
    fun tgSender(absSender: AbsSender) = TgAbsSender(absSender)

}

