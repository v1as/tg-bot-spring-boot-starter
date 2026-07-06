package ru.v1as.tg.starter.configuration.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import java.time.Duration

@ConfigurationProperties(prefix = "tg.bot")
class TgBotProperties(
    var token: String = "",
    var username: String = "",
    var runnable: Boolean = true,
    var gracefulShutdown: Boolean = true,
    var backOffPeriod: Duration? = null,
    var apiUrl: String? = null,
    var proxy: TgBotProxyProperties = TgBotProxyProperties(),
)
