package ru.v1as.tg.starter

import org.springframework.beans.factory.DisposableBean
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.meta.generics.BotSession
import org.telegram.telegrambots.meta.generics.LongPollingBot
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession

open class TgBotRunner(val tgBot: LongPollingBot) : ApplicationRunner, DisposableBean {

    private val telegramBotApi = TelegramBotsApi(DefaultBotSession::class.java)
    private var session: BotSession? = null

    override fun run(args: ApplicationArguments?) {
        this.session = telegramBotApi.registerBot(tgBot)
    }

    override fun destroy() {
        this.session?.stop()
    }
}