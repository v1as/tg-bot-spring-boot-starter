package ru.v1as.tg.starter.update.exception

import mu.KLogging
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import ru.v1as.tg.starter.TgSender
import ru.v1as.tg.starter.exceptions.TgMessageException
import ru.v1as.tg.starter.update.UpdateDataExtractor


class BaseUpdateProcessorExceptionHandler(
    private val tgSender: TgSender,
    private val updateDataExtractor: UpdateDataExtractor
) : UpdateProcessorExceptionHandler {

    companion object : KLogging()

    override fun handle(ex: Exception, update: Update) {
        if (ex is TgMessageException) {
            val chatId = updateDataExtractor.chatId(update).toString()
            tgSender.execute(SendMessage(chatId, ex.responseText))
            logger.debug { "Response message '${ex.responseText}' from exception to chatId $chatId" }
            return
        }
        logException(ex, update)
    }

    private fun logException(ex: Exception, update: Update) {
        logger.error(ex) { "Error while command $update handling" }
    }
}