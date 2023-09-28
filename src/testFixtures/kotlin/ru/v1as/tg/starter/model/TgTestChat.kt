package ru.v1as.tg.starter.model

import org.telegram.telegrambots.meta.api.objects.Chat


class TgTestChat(
    private val chatId: Long,
    private val type: String = GROUP_CHAT_TYPE,
    private val title: String = "",
    private val description: String = ""
) : TgChat {

    override fun getId(): Long = chatId

    override fun title(): String = title

    override fun description(): String = description

    override fun isUserChat(): Boolean = type == USER_CHAT_TYPE

    override fun model(): Chat {
        val chat = Chat()
        chat.let {
            it.id = chatId
            it.title = title
            it.description = description
            it.type = type
        }
        return chat
    }
}