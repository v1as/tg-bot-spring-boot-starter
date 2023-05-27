package ru.v1as.tg.starter.update

import org.springframework.core.Ordered
import org.telegram.telegrambots.meta.api.objects.Update
import ru.v1as.tg.starter.update.handle.Handled

interface UpdateHandler : Ordered {
    companion object {
        const val DEFAULT_PRECEDENCE = (Ordered.HIGHEST_PRECEDENCE + Ordered.LOWEST_PRECEDENCE) / 2
    }
    fun handle(update: Update): Handled
    override fun getOrder() = DEFAULT_PRECEDENCE
}