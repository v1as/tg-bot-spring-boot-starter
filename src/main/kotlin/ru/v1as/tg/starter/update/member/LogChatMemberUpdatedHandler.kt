package ru.v1as.tg.starter.update.member

import mu.KLogging
import org.springframework.core.Ordered
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMember
import ru.operation.handler.Handled
import ru.operation.handler.Handled.handled
import ru.operation.handler.Handled.skipped
import ru.v1as.tg.starter.model.base.TgUserWrapper
import ru.v1as.tg.starter.update.UpdateHandler

class LogChatMemberUpdatedHandler : UpdateHandler {

    companion object : KLogging()

    override fun handle(input: Update): Handled {
        val member: ChatMember = input.myChatMember?.newChatMember ?: return skipped()
        val user = TgUserWrapper(member.user)
        logger.debug { "Chat member ${user.usernameOrFullName()} updated status '${member.status}'" }
        return handled()
    }

    override fun getOrder() = Ordered.LOWEST_PRECEDENCE
}