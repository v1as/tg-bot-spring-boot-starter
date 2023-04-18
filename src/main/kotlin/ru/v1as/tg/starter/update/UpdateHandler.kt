package ru.v1as.tg.starter.update

import org.springframework.core.Ordered
import org.telegram.telegrambots.meta.api.objects.Update

interface UpdateHandler : Ordered {
    companion object {
        const val DEFAULT_PRECEDENCE = (Ordered.HIGHEST_PRECEDENCE + Ordered.LOWEST_PRECEDENCE) / 2
    }
    fun handle(update: Update): Boolean
    override fun getOrder() = DEFAULT_PRECEDENCE
}