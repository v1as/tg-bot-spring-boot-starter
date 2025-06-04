package ru.v1as.tg.starter.update

import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText
import org.telegram.telegrambots.meta.api.objects.message.Message
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton

fun editMessageText(block: EditMessageText.EditMessageTextBuilder<*, *>.() -> Unit): EditMessageText {
    val edit = EditMessageText.builder()
    edit.apply(block)
    return edit.build()
}

fun sendMessage(block: SendMessage.SendMessageBuilder<*, *>.() -> Unit): SendMessage {
    val message = SendMessage.builder()
    message.apply(block)
    return message.build()
}

fun answerCallbackQuery(block: AnswerCallbackQuery.AnswerCallbackQueryBuilder<*, *>.() -> Unit): AnswerCallbackQuery {
    val query = AnswerCallbackQuery.builder()
    query.apply(block)
    return query.build()
}

fun inlineKeyboardButton(block: InlineKeyboardButton.InlineKeyboardButtonBuilder<*, *>.() -> Unit): InlineKeyboardButton {
    val button = InlineKeyboardButton.builder()
    button.apply(block)
    return button.build()
}

fun Message.replySendMessage(block: SendMessage.SendMessageBuilder<*, *>.() -> Unit): SendMessage {
    val srcMsg = this
    return SendMessage.builder()
        .apply {
            srcMsg.replyToMessage?.messageThreadId?.let { messageThreadId(it) }
            chatId(srcMsg.chatId.toString())
            this.apply(block)
        }
        .build()
}
