package ru.v1as.tg.starter.configuration

import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.context.annotation.Lazy
import org.telegram.telegrambots.bots.DefaultBotOptions
import org.telegram.telegrambots.meta.bots.AbsSender
import org.telegram.telegrambots.meta.generics.LongPollingBot
import ru.v1as.tg.starter.TgAbsSender
import ru.v1as.tg.starter.TgBotProperties
import ru.v1as.tg.starter.TgBotRunner
import ru.v1as.tg.starter.TgLongPollingBot
import ru.v1as.tg.starter.update.*
import ru.v1as.tg.starter.update.exception.UpdateProcessorExceptionHandler
import ru.v1as.tg.starter.update.log.BaseMdcUpdate
import ru.v1as.tg.starter.update.log.MdcUpdate
import ru.v1as.tg.starter.update.member.LogChatMemberUpdatedHandler
import ru.v1as.tg.starter.update.request.BaseRequestUpdateHandler
import ru.v1as.tg.starter.update.request.RequestUpdateHandler

@AutoConfiguration
@ConditionalOnProperty("tg.bot.token")
@EnableConfigurationProperties(TgBotProperties::class)
@Import(TgCommandsConfiguration::class, TgCallbacksConfiguration::class)
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
    @ConditionalOnMissingBean(MdcUpdate::class)
    fun baseMdcUpdate(baseUpdateDataExtractor: BaseUpdateDataExtractor) = BaseMdcUpdate(
        baseUpdateDataExtractor
    )

    @Bean
    fun logChatMemberUpdatedHandler() = LogChatMemberUpdatedHandler()

    @Bean
    fun baseUpdateProcessor(
        beforeUpdateHandlers: List<BeforeUpdateHandler>,
        updateHandlers: List<UpdateHandler>,
        unmatchedUpdateHandler: UnmatchedUpdateHandler,
        afterUpdateHandlers: List<AfterUpdateHandler>,
        baseUpdateDataExtractor: BaseUpdateDataExtractor,
        updateProcessorExceptionHandler: UpdateProcessorExceptionHandler
    ) = BaseUpdateProcessor(
        beforeUpdateHandlers,
        updateHandlers,
        updateProcessorExceptionHandler,
        unmatchedUpdateHandler,
        afterUpdateHandlers,
    )

    @Bean
    @ConditionalOnMissingBean
    fun botOptions() = DefaultBotOptions()

    @Bean
    fun longPollingBot(
        options: DefaultBotOptions,
        props: TgBotProperties,
        updateProcessor: UpdateProcessor
    ) =
        TgLongPollingBot(options, props, updateProcessor)

    @Bean
    fun botRunner(tgBot: LongPollingBot, props: TgBotProperties) = TgBotRunner(tgBot, props)

    @Bean
    fun tgSender(@Lazy absSender: AbsSender) = TgAbsSender(absSender)

}

