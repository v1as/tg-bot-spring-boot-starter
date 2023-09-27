package ru.v1as.tg.starter.update.log

import mu.KLogging
import org.slf4j.MDC
import org.telegram.telegrambots.meta.api.objects.Update
import ru.v1as.tg.starter.update.BaseUpdateDataExtractor
import ru.v1as.tg.starter.update.UnsupportedUpdateException

private const val MDC_USER_ID = "USER_ID"
private const val MDC_CHAT_ID = "CHAT_ID"
private const val MDC_USER = "USER"
private const val MDC_CHAT = "CHAT"

class BaseMdcUpdate(private val updateDataExtractor: BaseUpdateDataExtractor) : MdcUpdate {

    companion object : KLogging()

    override fun set(update: Update) {
        try {
            val user = updateDataExtractor.user(update)
            val chat = updateDataExtractor.chat(update)
            MDC.setContextMap(
                mutableMapOf(
                    MDC_USER_ID to user.idStr(),
                    MDC_CHAT_ID to chat.idStr(),
                    MDC_USER to user.usernameOrFullName(),
                    MDC_CHAT to chat.title(),
                )
            )
        } catch (ex: UnsupportedUpdateException) {
            logger.debug { "MDC setting error: ${ex.message}" }
            logger.trace("Extraction error", ex)
        }
    }

    override fun clear() {
        listOf(
            MDC_USER_ID,
            MDC_CHAT_ID,
            MDC_USER,
            MDC_CHAT
        ).forEach { MDC.remove(it) }

    }

}