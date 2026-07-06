package ru.v1as.tg.starter.configuration

import org.telegram.telegrambots.meta.TelegramUrl
import java.net.URI

object TelegramUrlParser {

    fun parse(apiUrl: String): TelegramUrl {
        val uri =
            try {
                URI(apiUrl.trim())
            } catch (ex: java.net.URISyntaxException) {
                throw IllegalArgumentException("Invalid tg.bot.api-url: $apiUrl", ex)
            }
        val schema =
            uri.scheme?.takeIf { it.isNotBlank() }
                ?: throw IllegalArgumentException("tg.bot.api-url must include schema (http or https): $apiUrl")
        val host =
            uri.host?.takeIf { it.isNotBlank() }
                ?: throw IllegalArgumentException("tg.bot.api-url must include host: $apiUrl")
        val port =
            when {
                uri.port != -1 -> uri.port
                schema.equals("https", ignoreCase = true) -> 443
                else -> 80
            }
        return TelegramUrl.builder().schema(schema).host(host).port(port).build()
    }
}
