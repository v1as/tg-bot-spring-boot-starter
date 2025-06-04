package ru.v1as.tg.starter.configuration

import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer
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
@Import(TgCommandsConfiguration::class, TgCallbacksConfiguration::class, TgMessageConfiguration::class)
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
    fun httpTelegramClient(props: TgBotProperties) = OkHttpTelegramClient(props.token)

    @Bean
    fun longPollingBot(
        updateProcessor: UpdateProcessor
    ) = TgLongPollingBot(updateProcessor)

    @Bean
    fun botRunner(tgBot: LongPollingSingleThreadUpdateConsumer, props: TgBotProperties) = TgBotRunner(tgBot, props)

    @Bean
    fun tgSender(tgHttpClient: OkHttpTelegramClient) = TgAbsSender(tgHttpClient)

}

