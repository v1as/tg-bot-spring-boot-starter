package ru.v1as.tg.starter.update

import org.telegram.telegrambots.meta.api.objects.Update

interface UpdateDataExtractor {
    fun userId(update: Update): Long
    fun chatId(update: Update): Long
}