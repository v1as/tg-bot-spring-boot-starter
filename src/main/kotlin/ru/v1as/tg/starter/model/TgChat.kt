package ru.v1as.tg.starter.model

import org.telegram.telegrambots.meta.api.objects.Chat

interface TgChat {

    fun getId(): Long

    fun idStr() = getId().toString()

    fun title(): String

    fun description(): String

    fun isUserChat(): Boolean

    fun model(): Chat

}