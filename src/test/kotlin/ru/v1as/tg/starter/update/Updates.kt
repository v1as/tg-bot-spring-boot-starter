package ru.v1as.tg.starter.update

import org.telegram.telegrambots.meta.api.objects.*
import java.util.concurrent.atomic.AtomicInteger

private val messageIdCounter = AtomicInteger()


fun messageUpdate(chatId: Long = 1, userId: Long = 1, text: String = ""): Update {
    val user = User()
    user.id = userId

    val chat = Chat()
    chat.id = chatId

    val message = Message()
    message.from = user
    message.chat = chat
    message.text = text
    message.messageId = messageIdCounter.getAndIncrement()
    if (text.startsWith("/")) {
        val messageEntity = MessageEntity()
        messageEntity.text = text
        messageEntity.type = EntityType.BOTCOMMAND
        messageEntity.offset = 0
        message.entities = listOf(messageEntity)
    }

    val update = Update()
    update.message = message
    return update
}
