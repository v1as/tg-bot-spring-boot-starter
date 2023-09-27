package ru.v1as.tg.starter.exceptions

class TgCallbackAnswerException(val responseText: String) : TgBotMethodApiException()