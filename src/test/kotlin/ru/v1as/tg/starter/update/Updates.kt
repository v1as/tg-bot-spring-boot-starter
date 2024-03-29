package ru.v1as.tg.starter.update

import org.telegram.telegrambots.meta.api.objects.*
import java.util.concurrent.atomic.AtomicInteger

private val messageIdCounter = AtomicInteger()
private val updateIdCounter = AtomicInteger()


fun messageUpdate(text: String = "", chatId: Long = 1, userId: Long = 1): Update {
    val message = message(text, chatId, userId)

    val update = Update()
    update.updateId = updateIdCounter.incrementAndGet()
    update.message = message
    return update
}

fun message(
    text: String, chatId: Long = 1, userId: Long = 1
): Message {
    val user = User()
    user.id = userId
    user.userName = ""
    user.firstName = "Bob"

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
    return message
}
