package ru.v1as.tg.starter.model.base

import org.telegram.telegrambots.meta.api.objects.Chat
import ru.v1as.tg.starter.model.TgChat

class TgChatWrapper(val chat: Chat) : TgChat {

    override fun getId(): Long = chat.id

    override fun title(): String = chat.title ?: ""

    override fun description(): String = chat.description ?: ""

    override fun isUserChat(): Boolean = chat.isUserChat

    override fun model() = chat

}