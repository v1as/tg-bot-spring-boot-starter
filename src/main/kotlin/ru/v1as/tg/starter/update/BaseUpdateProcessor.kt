package ru.v1as.tg.starter.update

import mu.KotlinLogging
import org.telegram.telegrambots.meta.api.objects.Update
import ru.v1as.tg.starter.update.handle.Handled
import ru.v1as.tg.starter.update.handle.error
import ru.v1as.tg.starter.update.handle.unmatched

private val log = KotlinLogging.logger {}

open class BaseUpdateProcessor(
    private val beforeUpdateHandlers: List<BeforeUpdateHandler>,
    private val updateHandlers: List<UpdateHandler>,
    private val updateProcessorExceptionHandler: UpdateProcessorExceptionHandler,
    private val unmatchedUpdateHandler: UnmatchedUpdateHandler,
    private val afterUpdateHandlers: List<AfterUpdateHandler>,
) : UpdateProcessor {

    override fun process(update: Update) {
        var handled = unmatched()
        before(update)
        for (updateHandler in updateHandlers) {
            handled = handled.reduce(handle(updateHandler, update))
            if (handled.isDone()) {
                break
            }
        }
        handled.errors.forEach { updateProcessorExceptionHandler.handle(it, update) }
        if (!handled.isDone()) {
            unmatched(update)
        }
        after(update)
    }

    private fun handle(it: UpdateHandler, update: Update): Handled {
        var handled: Handled
        try {
            handled = it.handle(update)
        } catch (e: Exception) {
            handled = error(e)
        }
        return handled
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

    private fun after(update: Update) {
        afterUpdateHandlers.forEach {
            try {
                it.afterHandle(update)
            } catch (e: Exception) {
                log.error(e) { "Error while after update handling" }
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
}