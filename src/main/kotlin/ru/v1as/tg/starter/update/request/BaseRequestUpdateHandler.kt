package ru.v1as.tg.starter.update.request

import mu.KLogging
import org.telegram.telegrambots.meta.api.objects.Update
import ru.v1as.tg.starter.update.UpdateDataExtractor
import ru.v1as.tg.starter.update.UpdateHandler
import ru.v1as.tg.starter.update.handle.Handled
import ru.v1as.tg.starter.update.handle.error
import ru.v1as.tg.starter.update.handle.handled
import ru.v1as.tg.starter.update.handle.unmatched

open class BaseRequestUpdateHandler(private val updateDataExtractor: UpdateDataExtractor) :
    RequestUpdateHandler, UpdateHandler {
    companion object : KLogging()

    private val requests: MutableList<UpdateRequest> = mutableListOf()

    override fun register(request: UpdateRequest) {
        request.register { this.requests.remove(request) }
        this.requests.add(request)
    }

    override fun handle(update: Update): Handled {
        val chatId = updateDataExtractor.chat(update).getId()
        val userId = updateDataExtractor.user(update).id()
        val requestIterator = requests.iterator()
        for (request in requestIterator) {
            if (request.isExpired()) {
                requestIterator.remove()
                try {
                    request.onTimeout()
                } catch (e: Exception) {
                    logger.error("Error while request timeout:", e)
                }
            }
            if (request.chatId != null && chatId != request.chatId) {
                continue
            }
            if (request.userId != null && userId != request.userId) {
                continue
            }
            val filtered = try {
                request.filter(update)
            } catch (e: Exception) {
                logger.error("Error on request filtering", e)
                false
            }
            if (!filtered) {
                continue
            }
            try {
                request.onMatch.accept(update)
            } catch (e: Exception) {
                logger.error("Error on request match", e)
                requestIterator.remove()
                return error(e)
            }
            requestIterator.remove()
            return handled()
        }
        return unmatched()
    }

    override fun getOrder() = super.getOrder() + 100

}