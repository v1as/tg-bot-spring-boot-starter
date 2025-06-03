package ru.v1as.tg.starter.update.request

import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.message.Message
import ru.v1as.tg.starter.model.TgUser
import java.time.Duration
import java.time.LocalDateTime
import java.util.function.Consumer

fun replyOnMessageRequest(
    msg: Message, onMatch: Consumer<Update>, tgUser: TgUser? = null, timeout: Duration = Duration.ofMinutes(1)
) = UpdateRequest(onMatch,
    timeout,
    tgUser?.id(),
    msg.chatId,
    { it.message?.hasText() == true && it.message?.replyToMessage?.messageId == msg.messageId })

class UpdateRequest(
    val onMatch: Consumer<Update>,
    timeout: Duration,
    var userId: Long? = null,
    var chatId: Long? = null,
    var filter: (Update) -> Boolean = { true }, var onTimeout: () -> Unit = {}, private var cancel: Runnable? = null
) {
    private val expire: LocalDateTime = LocalDateTime.now().plus(timeout)

    fun register(cancel: Runnable) {
        this.cancel = cancel
    }

    fun cancel() = cancel?.run() ?: throw IllegalStateException()

    fun isExpired() = expire.isBefore(LocalDateTime.now())
}