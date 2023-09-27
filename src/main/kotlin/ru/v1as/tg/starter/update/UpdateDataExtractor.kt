package ru.v1as.tg.starter.update

import org.telegram.telegrambots.meta.api.objects.Update
import ru.v1as.tg.starter.model.TgChat
import ru.v1as.tg.starter.model.TgUser

interface UpdateDataExtractor {
    fun user(update: Update): TgUser
    fun chat(update: Update): TgChat
}