package ru.v1as.tg.starter.configuration.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "tg.bot")
class TgBotProperties(
    var token: String = "",
    var username: String = "",
    var runnable: Boolean = true,
    var gracefulShutdown: Boolean = true,
    var proxy: TgBotProxyProperties = TgBotProxyProperties()
)