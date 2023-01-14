package ru.v1as.tg.starter

import org.telegram.telegrambots.meta.api.objects.Update

interface TgUpdateProcessor {
    fun process(update: Update)
}