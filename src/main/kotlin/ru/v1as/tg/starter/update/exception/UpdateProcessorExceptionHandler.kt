package ru.v1as.tg.starter.update.exception

import org.telegram.telegrambots.meta.api.objects.Update

interface UpdateProcessorExceptionHandler {
    fun handle(ex: Exception, update: Update)
}