package ru.v1as.tg.starter.configuration

import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.telegram.telegrambots.meta.bots.AbsSender
import org.telegram.telegrambots.meta.generics.LongPollingBot
import ru.v1as.tg.starter.TgAbsSender
import ru.v1as.tg.starter.TgBotProperties
import ru.v1as.tg.starter.TgBotRunner
import ru.v1as.tg.starter.TgLongPollingBot
import ru.v1as.tg.starter.update.*
import ru.v1as.tg.starter.update.request.BaseRequestUpdateHandler
import ru.v1as.tg.starter.update.request.RequestUpdateHandler

@AutoConfiguration
@ConditionalOnProperty("tg.bot")
@EnableConfigurationProperties(TgBotProperties::class)
@Import(TgCommandsConfiguration::class)
class TgBotAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(UpdateDataExtractor::class)
    fun baseUpdateDataExtractor() = BaseUpdateDataExtractor()

    @Bean
    @ConditionalOnMissingBean(RequestUpdateHandler::class)
    fun baseRequestUpdateHandler(updateDataExtractor: UpdateDataExtractor) =
        BaseRequestUpdateHandler(updateDataExtractor)

    @Bean
    @ConditionalOnMissingBean(UnmatchedUpdateHandler::class)
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

