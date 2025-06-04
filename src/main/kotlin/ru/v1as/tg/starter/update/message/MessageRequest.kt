package ru.v1as.tg.starter.update.message

import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.message.Message

data class MessageRequest(val message: Message, val update: Update)