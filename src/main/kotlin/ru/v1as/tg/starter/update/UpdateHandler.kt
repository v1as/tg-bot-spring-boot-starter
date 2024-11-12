package ru.v1as.tg.starter.update

import org.springframework.core.Ordered
import org.telegram.telegrambots.meta.api.objects.Update
import ru.operation.handler.Handler

interface UpdateHandler : Handler<Update>, Ordered {
    companion object {
        const val DEFAULT_PRECEDENCE = (Ordered.HIGHEST_PRECEDENCE + Ordered.LOWEST_PRECEDENCE) / 2
    }

    override fun getOrder() = DEFAULT_PRECEDENCE
}