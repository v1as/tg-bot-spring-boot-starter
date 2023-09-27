package ru.v1as.tg.starter.update.log

import org.springframework.core.Ordered
import org.telegram.telegrambots.meta.api.objects.Update
import ru.v1as.tg.starter.update.AfterUpdateHandler
import ru.v1as.tg.starter.update.BeforeUpdateHandler

interface MdcUpdate : BeforeUpdateHandler, AfterUpdateHandler, Ordered {

    fun set(update: Update)

    fun clear()

    override fun beforeHandle(update: Update) {
        set(update)
    }

    override fun afterHandle(update: Update) {
        clear()
    }

    override fun getOrder() = Ordered.LOWEST_PRECEDENCE
}