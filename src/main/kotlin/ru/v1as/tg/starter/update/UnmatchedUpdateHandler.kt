package ru.v1as.tg.starter.update

import org.telegram.telegrambots.meta.api.objects.Update
import ru.operation.handler.Handler

fun interface UnmatchedUpdateHandler : Handler<Update>