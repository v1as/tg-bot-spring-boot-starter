package ru.v1as.tg.starter.configuration

import com.fasterxml.jackson.databind.ObjectMapper
import java.net.Authenticator
import java.net.InetSocketAddress
import java.net.PasswordAuthentication
import java.net.Proxy
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit.SECONDS
import mu.KLogging
import okhttp3.ConnectionPool
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.context.annotation.Lazy
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication
import org.telegram.telegrambots.longpolling.interfaces.BackOff
import org.telegram.telegrambots.longpolling.util.ExponentialBackOff
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer
import ru.operation.handler.Handled.skipped
import ru.v1as.tg.starter.TgAbsSender
import ru.v1as.tg.starter.TgBotRunner
import ru.v1as.tg.starter.TgLongPollingBot
import ru.v1as.tg.starter.configuration.properties.TgBotProperties
import ru.v1as.tg.starter.update.*
import ru.v1as.tg.starter.update.exception.UpdateProcessorExceptionHandler
import ru.v1as.tg.starter.update.log.BaseMdcUpdate
import ru.v1as.tg.starter.update.log.MdcUpdate
import ru.v1as.tg.starter.update.member.LogChatMemberUpdatedHandler
import ru.v1as.tg.starter.update.request.BaseRequestUpdateHandler
import ru.v1as.tg.starter.update.request.RequestUpdateHandler

const val OK_HTTP_CLIENT_BEAN = "tgOkHttpClient"

@AutoConfiguration
@ConditionalOnProperty("tg.bot.token")
@EnableConfigurationProperties(TgBotProperties::class)
@Import(
    TgCommandsConfiguration::class,
    TgCallbacksConfiguration::class,
    TgMessageConfiguration::class,
)
class TgBotAutoConfiguration {

    companion object : KLogging()

    @Bean
    @ConditionalOnMissingBean(UpdateDataExtractor::class)
    fun baseUpdateDataExtractor() = BaseUpdateDataExtractor()

    @Bean
    @ConditionalOnMissingBean(RequestUpdateHandler::class)
    fun baseRequestUpdateHandler(updateDataExtractor: UpdateDataExtractor) =
        BaseRequestUpdateHandler(updateDataExtractor)

    @Bean
    @ConditionalOnMissingBean(UnmatchedUpdateHandler::class)
    fun unmatchedUpdateHandlerStub() = UnmatchedUpdateHandler {skipped() }

    @Bean
    @ConditionalOnMissingBean(MdcUpdate::class)
    fun baseMdcUpdate(baseUpdateDataExtractor: BaseUpdateDataExtractor) =
        BaseMdcUpdate(baseUpdateDataExtractor)

    @Bean fun logChatMemberUpdatedHandler() = LogChatMemberUpdatedHandler()

    @Bean
    @ConditionalOnMissingBean
    fun baseUpdateProcessor(
        beforeUpdateHandlers: List<BeforeUpdateHandler>,
        updateHandlers: List<UpdateHandler>,
        unmatchedUpdateHandler: UnmatchedUpdateHandler,
        afterUpdateHandlers: List<AfterUpdateHandler>,
        updateProcessorExceptionHandler: UpdateProcessorExceptionHandler,
    ) =
        BaseUpdateProcessor(
            beforeUpdateHandlers,
            updateHandlers,
            updateProcessorExceptionHandler,
            unmatchedUpdateHandler,
            afterUpdateHandlers,
        )

    @Bean(OK_HTTP_CLIENT_BEAN)
    @ConditionalOnMissingBean(name = [OK_HTTP_CLIENT_BEAN], value = [OkHttpTelegramClient::class])
    fun tgOkHttpClient(props: TgBotProperties): OkHttpClient {

        val dispatcher = Dispatcher()
        dispatcher.maxRequests = 100
        dispatcher.maxRequestsPerHost = 100

        val httpClient =
            OkHttpClient()
                .newBuilder()
                .dispatcher(dispatcher)
                .connectionPool(ConnectionPool(100, 75L, SECONDS))
                .readTimeout(100L, SECONDS)
                .writeTimeout(70L, SECONDS)
                .connectTimeout(75L, SECONDS)

        val proxyProps = props.proxy
        if (proxyProps.host.isNotBlank()) {
            val proxy = Proxy(proxyProps.type, InetSocketAddress(proxyProps.host, proxyProps.port))
            var logMessage =
                "Built tg http client with host: ${proxyProps.host}, port: ${proxyProps.port}"
            if (proxyProps.username.isNotBlank()) {
                if (proxyProps.username.isNotBlank() && proxyProps.password.isNotBlank()) {
                    Authenticator.setDefault(
                        object : Authenticator() {
                            override fun getPasswordAuthentication() =
                                PasswordAuthentication(
                                    proxyProps.username,
                                    proxyProps.password.toCharArray(),
                                )
                        }
                    )
                    logMessage += ", user: ${proxyProps.username}, password: ***"
                }
            } else {
                logMessage += ", no auth"
            }
            httpClient.proxy(proxy)
            logger.info(logMessage)
        } else {
            logger.info("Built tg http client without proxy.")
        }
        return httpClient.build()
    }

    @Bean
    @ConditionalOnMissingBean
    fun httpTelegramClient(
        props: TgBotProperties,
        @Qualifier(OK_HTTP_CLIENT_BEAN) httpClient: OkHttpClient,
    ) = OkHttpTelegramClient(httpClient, props.token)

    @Bean
    @ConditionalOnMissingBean
    fun longPollingBot(updateProcessor: UpdateProcessor) = TgLongPollingBot(updateProcessor)

    @Bean @ConditionalOnMissingBean fun fixedDurationBackOff(): BackOff = ExponentialBackOff()

    @Bean
    @ConditionalOnMissingBean
    fun longPollingBotApplication(
        @Qualifier(OK_HTTP_CLIENT_BEAN) httpClient: OkHttpClient,
        backOff: BackOff,
    ) =
        TelegramBotsLongPollingApplication(
            { ObjectMapper() },
            { httpClient },
            Executors::newSingleThreadScheduledExecutor,
            { backOff },
        )

    @Bean
    fun botRunner(
        tgBot: LongPollingSingleThreadUpdateConsumer,
        botApplication: TelegramBotsLongPollingApplication,
        props: TgBotProperties,
    ) = TgBotRunner(tgBot, botApplication, props)

    @Bean
    @ConditionalOnMissingBean
    fun tgSender(tgHttpClient: OkHttpTelegramClient) = TgAbsSender(tgHttpClient)
}
