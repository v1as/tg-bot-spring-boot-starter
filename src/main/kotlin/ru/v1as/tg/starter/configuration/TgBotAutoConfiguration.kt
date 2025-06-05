package ru.v1as.tg.starter.configuration

import okhttp3.OkHttpClient
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer
import ru.v1as.tg.starter.TgAbsSender
import ru.v1as.tg.starter.TgBotRunner
import ru.v1as.tg.starter.TgLongPollingBot
import ru.v1as.tg.starter.configuration.properties.TgBotProperties
import ru.v1as.tg.starter.update.AfterUpdateHandler
import ru.v1as.tg.starter.update.BaseUpdateDataExtractor
import ru.v1as.tg.starter.update.BaseUpdateProcessor
import ru.v1as.tg.starter.update.BeforeUpdateHandler
import ru.v1as.tg.starter.update.UnmatchedUpdateHandler
import ru.v1as.tg.starter.update.UpdateDataExtractor
import ru.v1as.tg.starter.update.UpdateHandler
import ru.v1as.tg.starter.update.UpdateProcessor
import ru.v1as.tg.starter.update.exception.UpdateProcessorExceptionHandler
import ru.v1as.tg.starter.update.log.BaseMdcUpdate
import ru.v1as.tg.starter.update.log.MdcUpdate
import ru.v1as.tg.starter.update.member.LogChatMemberUpdatedHandler
import ru.v1as.tg.starter.update.request.BaseRequestUpdateHandler
import ru.v1as.tg.starter.update.request.RequestUpdateHandler
import java.net.Authenticator
import java.net.InetSocketAddress
import java.net.PasswordAuthentication
import java.net.Proxy

const val OK_HTTP_CLIENT_BEAN = "tgOkHttpClient"

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

    @Bean(OK_HTTP_CLIENT_BEAN)
    @ConditionalOnMissingBean(name = [OK_HTTP_CLIENT_BEAN], value = [OkHttpTelegramClient::class])
    fun tgOkHttpClient(props: TgBotProperties): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        val proxyProps = props.proxy
        if (proxyProps.host.isNotBlank()) {
            val proxy = Proxy(proxyProps.type, InetSocketAddress(proxyProps.host, proxyProps.port))
            if (proxyProps.username.isNotBlank()) {
                if (proxyProps.username.isNotBlank() && proxyProps.password.isNotBlank()) {
                    Authenticator.setDefault(object : Authenticator() {
                        override fun getPasswordAuthentication() =
                            PasswordAuthentication(proxyProps.username, proxyProps.password.toCharArray())
                    })
                }
            }
            httpClient.proxy(proxy)
        }
        return httpClient.build()
    }

    @Bean
    @ConditionalOnMissingBean
    fun httpTelegramClient(props: TgBotProperties, @Qualifier(OK_HTTP_CLIENT_BEAN) httpClient: OkHttpClient) =
        OkHttpTelegramClient(httpClient, props.token)

    @Bean
    fun longPollingBot(
        updateProcessor: UpdateProcessor
    ) = TgLongPollingBot(updateProcessor)

    @Bean
    fun botRunner(tgBot: LongPollingSingleThreadUpdateConsumer, props: TgBotProperties) = TgBotRunner(tgBot, props)

    @Bean
    fun tgSender(tgHttpClient: OkHttpTelegramClient) = TgAbsSender(tgHttpClient)

}

