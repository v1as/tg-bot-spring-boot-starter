package ru.v1as.tg.starter.update.request

import org.telegram.telegrambots.meta.api.objects.Update
import java.time.Duration
import java.time.LocalDateTime
import java.util.function.Predicate

class UpdateRequest constructor(
    val onMatch: Predicate<Update>,
    timeout: Duration,
    var userId: Long? = null,
    var chatId: Long? = null,
    var onTimeout: () -> Unit = {},
    var filter: (Update) -> Boolean = { true },
) {
    val expire: LocalDateTime = LocalDateTime.now().plus(timeout)
}