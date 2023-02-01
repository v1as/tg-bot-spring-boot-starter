package ru.v1as.tg.starter.update

import org.telegram.telegrambots.meta.api.objects.Update

fun interface UpdateHandler {
    fun handle(update: Update): Boolean
}