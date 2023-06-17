package ru.v1as.tg.starter.exceptions

class TgMessageException(val responseText: String) : TgBotMethodApiException()