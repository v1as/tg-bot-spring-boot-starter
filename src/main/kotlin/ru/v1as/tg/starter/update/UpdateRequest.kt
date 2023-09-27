package ru.v1as.tg.starter.update

import org.telegram.telegrambots.meta.api.objects.Update
import ru.v1as.tg.starter.model.TgChat
import ru.v1as.tg.starter.model.TgUser

open class UpdateRequest(
    open val update: Update, val from: TgUser, val chat: TgChat
)