package ru.v1as.tg.starter.update

import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText

fun editMessageText(block: EditMessageText.() -> Unit): EditMessageText {
    val edit = EditMessageText()
    edit.apply(block)
    return edit
}

fun sendMessage(block: SendMessage.() -> Unit): SendMessage {
    val message = SendMessage()
    message.apply(block)
    return message
}

fun answerCallbackQuery(block: AnswerCallbackQuery.() -> Unit): AnswerCallbackQuery {
    val query = AnswerCallbackQuery()
    query.apply(block)
    return query
}