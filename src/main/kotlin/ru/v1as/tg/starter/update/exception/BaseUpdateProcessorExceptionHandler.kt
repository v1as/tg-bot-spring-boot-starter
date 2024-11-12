package ru.v1as.tg.starter.update.exception

import mu.KLogging
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import ru.operation.Failed
import ru.operation.handler.Handled
import ru.operation.handler.Handled.handled
import ru.v1as.tg.starter.TgSender
import ru.v1as.tg.starter.exceptions.TgCallbackAnswerException
import ru.v1as.tg.starter.exceptions.TgMessageException
import ru.v1as.tg.starter.update.UpdateDataExtractor


class BaseUpdateProcessorExceptionHandler(
    private val tgSender: TgSender,
    private val updateDataExtractor: UpdateDataExtractor
) : UpdateProcessorExceptionHandler {

    companion object : KLogging()

    override fun handle(failed: Failed<Update>): Handled {
        val ex = failed.exception()
        val update = failed.value()
        if (ex is TgMessageException) {
            val chatId = updateDataExtractor.chat(update).idStr()
            tgSender.execute(SendMessage(chatId, ex.responseText))
            logger.debug { "Response message '${ex.responseText}' from exception to chatId $chatId" }
            return handled()
        } else if (ex is TgCallbackAnswerException) {
            val answerCallbackQuery =
                AnswerCallbackQuery.builder().callbackQueryId(update.callbackQuery.id)
                    .text(ex.responseText).build()
            tgSender.execute(answerCallbackQuery)
            return handled()
        }
        logException(ex, update)
        return error(ex)
    }

    private fun logException(ex: Exception, update: Update) {
        logger.error(ex) { "Error while command $update handling" }
    }
}