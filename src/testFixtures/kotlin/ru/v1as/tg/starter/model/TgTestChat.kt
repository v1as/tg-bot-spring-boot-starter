package ru.v1as.tg.starter.model

import org.telegram.telegrambots.meta.api.objects.chat.Chat


class TgTestChat(
    private val chatId: Long,
    private val type: String = GROUP_CHAT_TYPE,
    private val title: String = ""
) : TgChat {

    override fun getId(): Long = chatId

    override fun title(): String = title

    override fun isUserChat(): Boolean = type == USER_CHAT_TYPE

    override fun model(): Chat {
        val chat = Chat(getId(), type)
        chat.title = title
        return chat
    }
}