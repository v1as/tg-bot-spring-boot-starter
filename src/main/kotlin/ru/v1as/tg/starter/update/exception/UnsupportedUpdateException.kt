package ru.v1as.tg.starter.update.exception

import org.telegram.telegrambots.meta.api.objects.Update

class UnsupportedUpdateException(val update: Update, message: String? = null) :
    RuntimeException(message)