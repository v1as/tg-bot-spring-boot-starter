package ru.v1as.tg.starter.configuration

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

class TelegramUrlParserTest {

    @Test
    fun `parses http url with port`() {
        val url = TelegramUrlParser.parse("http://telegram-bot-api:8081")

        assertThat(url.schema).isEqualTo("http")
        assertThat(url.host).isEqualTo("telegram-bot-api")
        assertThat(url.port).isEqualTo(8081)
    }

    @Test
    fun `parses https url without port`() {
        val url = TelegramUrlParser.parse("https://api.telegram.org")

        assertThat(url.schema).isEqualTo("https")
        assertThat(url.host).isEqualTo("api.telegram.org")
        assertThat(url.port).isEqualTo(443)
    }

    @Test
    fun `parses http url without port`() {
        val url = TelegramUrlParser.parse("http://localhost")

        assertThat(url.schema).isEqualTo("http")
        assertThat(url.host).isEqualTo("localhost")
        assertThat(url.port).isEqualTo(80)
    }

    @Test
    fun `rejects url without host`() {
        assertThatThrownBy { TelegramUrlParser.parse("http://") }
            .isInstanceOf(IllegalArgumentException::class.java)
    }
}
