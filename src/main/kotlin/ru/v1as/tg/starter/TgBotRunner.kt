package ru.v1as.tg.starter

import mu.KotlinLogging
import org.springframework.beans.factory.DisposableBean
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.meta.generics.BotSession
import org.telegram.telegrambots.meta.generics.LongPollingBot
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession

private val log = KotlinLogging.logger {}

open class TgBotRunner(
    private val tgBot: LongPollingBot,
    private val props: TgBotProperties
) : ApplicationRunner, DisposableBean {

    private val telegramBotApi = TelegramBotsApi(DefaultBotSession::class.java)
    private var session: BotSession? = null

    override fun run(args: ApplicationArguments?) {
        if (props.runnable) {
            this.session = telegramBotApi.registerBot(tgBot)
            log.info { "Bot '${tgBot.botUsername}' started" }
        } else{
            log.info { "Bot '${tgBot.botUsername}' is not runnable" }
        }
    }

    override fun destroy() {
        this.session?.stop()
        log.info { "Bot '${tgBot.botUsername}' stopped" }
    }
}