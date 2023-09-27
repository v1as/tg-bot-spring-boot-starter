package ru.v1as.tg.starter

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "tg.bot")
class TgBotProperties(
    var token: String = "",
    var username: String = "",
    var runnable: Boolean = true
)