package ru.v1as.tg.starter.update.request

import org.telegram.telegrambots.meta.api.objects.Update
import ru.v1as.tg.starter.update.UpdateDataExtractor

open class BaseRequestUpdateHandler(private val updateDataExtractor: UpdateDataExtractor) : RequestUpdateHandler {
    private val requests: MutableList<UpdateRequest> = mutableListOf()

    override fun register(request: UpdateRequest) {
        this.requests.add(request)
    }

    override fun handle(update: Update): Boolean {
        val chatId = updateDataExtractor.chatId(update)
        val userId = updateDataExtractor.userId(update)
        val requestIterator = requests.iterator()
        for (request in requestIterator) {
            if (request.chatId != null && chatId != request.chatId) {
                continue
            }
            if (request.userId != null && userId != request.userId) {
                continue
            }
            if (!request.filter(update)) {
                continue
            }
            if (request.onMatch.test(update)) {
                requestIterator.remove()
            }
            return true
        }
        return false
    }

    override fun getOrder() = super.getOrder() + 100

}