package ru.v1as.tg.starter.test.sender

import java.util.concurrent.atomic.AtomicInteger
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Chat
import org.telegram.telegrambots.meta.api.objects.Message

class SendMessageMethodHandler : TgSenderMethodHandler<Message, SendMessage> {

    private val idCounter = AtomicInteger()

    override fun execute(method: SendMessage): Message {
        val message = Message()
        message.messageId = idCounter.incrementAndGet()
        message.text = method.text
        val chat = Chat()
        chat.id = method.chatId.toLong()
        message.chat = chat
        return message
    }
}
