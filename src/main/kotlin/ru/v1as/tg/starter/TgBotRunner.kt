package ru.v1as.tg.starter

import mu.KotlinLogging
import org.springframework.beans.factory.DisposableBean
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer
import ru.v1as.tg.starter.configuration.properties.TgBotProperties

private val log = KotlinLogging.logger {}

open class TgBotRunner(
    private val tgBot: LongPollingSingleThreadUpdateConsumer,
    private val props: TgBotProperties
) : ApplicationRunner, DisposableBean {

    private val botsApplication = TelegramBotsLongPollingApplication()

    override fun run(args: ApplicationArguments?) {
        if (props.runnable) {
            botsApplication.registerBot(props.token, tgBot)
            log.info { "Bot '${props.username}' started" }
        } else {
            log.info { "Bot '${props.username}' is not runnable" }
        }
    }

    override fun destroy() {
        if (props.gracefulShutdown) {
            this.botsApplication.close()
            log.info { "Bot '${props.username}' stopped" }
        }
    }
}