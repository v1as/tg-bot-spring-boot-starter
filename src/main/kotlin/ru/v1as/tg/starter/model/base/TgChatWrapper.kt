package ru.v1as.tg.starter.model.base

import org.telegram.telegrambots.meta.api.objects.chat.Chat
import ru.v1as.tg.starter.model.TgChat

class TgChatWrapper(val chat: Chat) : TgChat {

    override fun getId(): Long = chat.id

    override fun title(): String = chat.title ?: ""

    override fun isUserChat(): Boolean = chat.isUserChat

    override fun model() = chat

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is TgChat) return false

        if (getId() != other.getId()) return false

        return true
    }

    override fun hashCode(): Int {
        return getId().hashCode()
    }


}