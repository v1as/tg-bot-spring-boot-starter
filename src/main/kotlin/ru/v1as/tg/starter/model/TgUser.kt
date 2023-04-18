package ru.v1as.tg.starter.model

import org.telegram.telegrambots.meta.api.objects.User

interface TgUser : Comparable<TgUser> {
    fun id(): Long

    fun idStr(): String = id().toString()

    fun userName(): String

    fun firstName(): String

    fun lastName(): String

    fun languageCode(): String

    fun fullName(): String = "${firstName()} ${lastName()}"

    fun usernameOrFullName() =
        if (userName().isEmpty()) "@${userName()}" else fullName()

    override fun compareTo(other: TgUser) = id().compareTo(other.id())

    fun model(): User

}