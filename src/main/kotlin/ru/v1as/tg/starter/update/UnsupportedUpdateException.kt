package ru.v1as.tg.starter.update

import org.telegram.telegrambots.meta.api.objects.Update

class UnsupportedUpdateException(val update: Update) : RuntimeException() {

}