package ru.v1as.tg.starter.update.command

import mu.KotlinLogging
import ru.v1as.tg.starter.model.base.TgChatWrapper
import ru.v1as.tg.starter.model.base.TgUserWrapper
import ru.v1as.tg.starter.update.command.Handled.*

private val log = KotlinLogging.logger {}

abstract class AbstractCommandHandler(
    private val commandName: String,
    private val description: String = ""
) : CommandHandler {


    override fun handle(command: CommandRequest): Handled {
        if (command.name != commandName) {
            return Skipped
        }
        val user = TgUserWrapper(command.message.from)
        val chat = TgChatWrapper(command.message.chat)
        try {
            handle(command, user, chat)
        } catch (e: Exception) {
            log.error(e) { "Error for command handling $command" }
            return Error
        }
        return Done
    }

    abstract fun handle(command: CommandRequest, user: TgUserWrapper, chat: TgChatWrapper)

    override fun description() = description.ifEmpty { commandName }
}