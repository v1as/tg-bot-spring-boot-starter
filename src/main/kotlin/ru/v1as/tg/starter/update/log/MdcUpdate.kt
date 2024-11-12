package ru.v1as.tg.starter.update.log

import org.springframework.core.Ordered
import org.telegram.telegrambots.meta.api.objects.Update
import ru.operation.handler.Handled
import ru.operation.handler.Handled.handled
import ru.v1as.tg.starter.update.AfterUpdateHandler
import ru.v1as.tg.starter.update.BeforeUpdateHandler

interface MdcUpdate : BeforeUpdateHandler, AfterUpdateHandler, Ordered {

    fun set(update: Update)

    fun clear()

    override fun beforeHandle(update: Update): Handled {
        set(update)
        return handled()
    }

    override fun afterHandle(update: Update): Handled {
        clear()
        return handled()
    }

    override fun getOrder() = Ordered.LOWEST_PRECEDENCE
}