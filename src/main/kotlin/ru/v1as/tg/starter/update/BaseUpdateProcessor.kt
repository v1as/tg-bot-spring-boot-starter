package ru.v1as.tg.starter.update

import mu.KotlinLogging
import org.telegram.telegrambots.meta.api.objects.Update

private val log = KotlinLogging.logger {}

open class BaseUpdateProcessor(
    private val beforeUpdateHandlers: List<BeforeUpdateHandler>,
    private val updateHandlers: List<UpdateHandler>,
    private val unmatchedUpdateHandler: UnmatchedUpdateHandler,
    private val afterUpdateHandlers: List<AfterUpdateHandler>,
) : UpdateProcessor {
    override fun process(update: Update) {
        before(update)
        updateHandlers.find { handle(it, update) } ?: unmatched(update)
        after(update)
    }

    private fun after(update: Update) {
        afterUpdateHandlers.forEach {
            try {
                it.afterHandle(update)
            } catch (e: Exception) {
                log.error(e) { "Error while after update handling" }
            }
        }
    }

    private fun before(update: Update) {
        beforeUpdateHandlers.forEach {
            try {
                it.beforeHandle(update)
            } catch (e: Exception) {
                log.error(e) { "Error while before update handling" }
            }
        }
    }

    private fun unmatched(update: Update) {
        try {
            unmatchedUpdateHandler.unmatchedHandle(update)
        } catch (e: Exception) {
            log.error(e) { "Error while unmatched update handling" }
        }
    }

    private fun handle(it: UpdateHandler, update: Update): Boolean {
        return try {
            it.handle(update)
        } catch (e: Exception) {
            log.error(e) { "Error while update handling" }
            false
        }
    }
}