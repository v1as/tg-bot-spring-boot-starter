package ru.v1as.tg.starter.update

import org.telegram.telegrambots.meta.api.objects.Update

interface UpdateProcessor {
    fun process(update: Update)
}