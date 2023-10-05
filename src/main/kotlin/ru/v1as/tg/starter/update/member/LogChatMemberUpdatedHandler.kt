package ru.v1as.tg.starter.update.member

import mu.KLogging
import org.springframework.core.Ordered
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMember
import ru.v1as.tg.starter.model.base.TgUserWrapper
import ru.v1as.tg.starter.update.UpdateHandler
import ru.v1as.tg.starter.update.handle.Handled
import ru.v1as.tg.starter.update.handle.handled
import ru.v1as.tg.starter.update.handle.unmatched

class LogChatMemberUpdatedHandler : UpdateHandler {

    companion object : KLogging()

    override fun handle(input: Update): Handled {
        val member: ChatMember = input.myChatMember?.newChatMember ?: return unmatched()
        val user = TgUserWrapper(member.user)
        logger.debug { "Chat member ${user.usernameOrFullName()} updated status '${member.status}'" }
        return handled()
    }

    override fun getOrder() = Ordered.LOWEST_PRECEDENCE
}