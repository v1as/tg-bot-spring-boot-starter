package ru.v1as.tg.starter.model

import org.telegram.telegrambots.meta.api.objects.chat.Chat


const val USER_CHAT_TYPE = "private"
const val GROUP_CHAT_TYPE = "group"
const val CHANNEL_CHAT_TYPE = "channel"
const val SUPER_GROUP_CHAT_TYPE = "supergroup"

interface TgChat {

    fun getId(): Long

    fun idStr() = getId().toString()

    fun title(): String

    fun isUserChat(): Boolean

    fun model(): Chat

}