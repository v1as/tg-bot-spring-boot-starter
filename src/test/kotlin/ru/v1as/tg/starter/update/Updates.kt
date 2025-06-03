package ru.v1as.tg.starter.update

import org.telegram.telegrambots.meta.api.objects.*
import org.telegram.telegrambots.meta.api.objects.chat.Chat
import org.telegram.telegrambots.meta.api.objects.message.Message
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
    val user = User(userId, "Bob", false)
    user.userName = ""

    val chat = Chat(chatId, "group")

    val message = Message()
    message.from = user
    message.chat = chat
    message.text = text
    message.messageId = messageIdCounter.getAndIncrement()
    if (text.startsWith("/")) {
        val messageEntity = MessageEntity(EntityType.BOTCOMMAND, 0, text.length)
        messageEntity.text = text
        message.entities = listOf(messageEntity)
    }
    return message
}
