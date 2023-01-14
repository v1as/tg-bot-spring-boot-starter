package ru.v1as.tg.starter

import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.objects.Update

class TgLongPollingBot(val props: TgBotProperties, val updateProcessor: TgUpdateProcessor) :
    TelegramLongPollingBot() {

    override fun getBotToken() = props.token

    override fun getBotUsername() = props.username

    override fun onUpdateReceived(update: Update?) {
        if (update != null) {
            updateProcessor.process(update)
        }
    }
}