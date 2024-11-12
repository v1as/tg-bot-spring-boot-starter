package ru.v1as.tg.starter.update.command

import ru.operation.handler.AbstractHandler
import ru.operation.handler.Handled
import ru.operation.handler.Handled.handled
import ru.v1as.tg.starter.exceptions.TgBotMethodApiException
import ru.v1as.tg.starter.model.base.TgChatWrapper
import ru.v1as.tg.starter.model.base.TgUserWrapper
import java.lang.Exception

abstract class AbstractCommandHandler(
    private val commandName: String,
    private val description: String = ""
) : AbstractHandler<CommandRequest>(), CommandHandler {

    override fun check(input: CommandRequest) = input.name == commandName

    override fun handleInternal(command: CommandRequest): Handled {
        val user = TgUserWrapper(command.message.from)
        val chat = TgChatWrapper(command.message.chat)
        handle(command, user, chat)
        return handled()
    }

    abstract fun handle(command: CommandRequest, user: TgUserWrapper, chat: TgChatWrapper)

    override fun onException(input: CommandRequest, ex: Exception) =
        if (ex is TgBotMethodApiException)
            throw ex
        else super.onException(input, ex)

    override fun description() = description.ifEmpty { commandName }
}