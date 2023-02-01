package ru.v1as.tg.starter.model.base

import org.telegram.telegrambots.meta.api.objects.User
import ru.v1as.tg.starter.model.TgUser

class TgUserWrapper(val user: User) : TgUser {

    override fun id(): Long = user.id

    override fun userName(): String = user.userName ?: ""

    override fun firstName(): String = user.firstName

    override fun lastName(): String = user.lastName ?: ""

    override fun languageCode(): String = user.languageCode ?: ""

    override fun model() = user
}