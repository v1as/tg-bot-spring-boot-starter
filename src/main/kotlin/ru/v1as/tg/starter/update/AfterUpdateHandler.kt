package ru.v1as.tg.starter.update

import org.telegram.telegrambots.meta.api.objects.Update
import ru.operation.handler.Handled

@FunctionalInterface
fun interface AfterUpdateHandler {
    fun afterHandle(update: Update): Handled
}