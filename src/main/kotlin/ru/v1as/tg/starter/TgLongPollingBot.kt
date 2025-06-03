package ru.v1as.tg.starter

import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer
import org.telegram.telegrambots.meta.api.objects.Update
import ru.v1as.tg.starter.update.UpdateProcessor

open class TgLongPollingBot(
    private val updateProcessor: UpdateProcessor
) : LongPollingSingleThreadUpdateConsumer {

    override fun consume(update: Update?) {
        if (update != null) {
            updateProcessor.process(update)
        }
    }
}