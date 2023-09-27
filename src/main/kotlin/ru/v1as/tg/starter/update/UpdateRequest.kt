package ru.v1as.tg.starter.update

import org.telegram.telegrambots.meta.api.objects.Update
import ru.v1as.tg.starter.model.TgChat
import ru.v1as.tg.starter.model.TgUser

abstract class UpdateRequest(
    val update: Update, val from: TgUser, val chat: TgChat
)