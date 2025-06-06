package ru.v1as.tg.starter.configuration.properties

import java.time.Duration
import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "tg.bot")
class TgBotProperties(
    var token: String = "",
    var username: String = "",
    var runnable: Boolean = true,
    var gracefulShutdown: Boolean = true,
    var backOffPeriod: Duration? = null,
    var proxy: TgBotProxyProperties = TgBotProxyProperties(),
)
