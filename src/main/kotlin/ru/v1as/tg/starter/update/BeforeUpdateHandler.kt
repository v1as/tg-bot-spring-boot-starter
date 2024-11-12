package ru.v1as.tg.starter.update

import org.telegram.telegrambots.meta.api.objects.Update
import ru.operation.handler.Handled

fun interface BeforeUpdateHandler {
    fun beforeHandle(update: Update): Handled
}