package ru.v1as.tg.starter.update.command

import mu.KotlinLogging
import ru.v1as.tg.starter.exceptions.TgBotMethodApiException
import ru.v1as.tg.starter.model.base.TgChatWrapper
import ru.v1as.tg.starter.model.base.TgUserWrapper
import ru.v1as.tg.starter.update.handle.Handled
import ru.v1as.tg.starter.update.handle.error
import ru.v1as.tg.starter.update.handle.handled
import ru.v1as.tg.starter.update.handle.unmatched

private val log = KotlinLogging.logger {}

abstract class AbstractCommandHandler(
    private val commandName: String,
    private val description: String = ""
) : CommandHandler {


    override fun handle(command: CommandRequest): Handled {
        if (match(command)) {
            return unmatched()
        }
        val user = TgUserWrapper(command.message.from)
        val chat = TgChatWrapper(command.message.chat)
        try {
            handle(command, user, chat)
        } catch (ex: TgBotMethodApiException) {
            throw ex
        } catch (ex: Exception) {
            log.error(ex) { "Error for command handling $command" }
            return error(ex)
        }
        return handled()
    }

    protected open fun match(command: CommandRequest): Boolean =
        command.name != commandName

    abstract fun handle(command: CommandRequest, user: TgUserWrapper, chat: TgChatWrapper)

    override fun description() = description.ifEmpty { commandName }
}