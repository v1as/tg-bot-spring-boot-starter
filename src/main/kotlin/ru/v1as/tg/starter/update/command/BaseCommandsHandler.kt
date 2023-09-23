package ru.v1as.tg.starter.update.command

import mu.KotlinLogging
import org.telegram.telegrambots.meta.api.objects.Update
import ru.v1as.tg.starter.update.UpdateHandler
import ru.v1as.tg.starter.update.handle.AbstractListHandler
import ru.v1as.tg.starter.update.handle.Handler

private val log = KotlinLogging.logger {}

class BaseCommandsHandler(
    commandHandlers: List<CommandHandler>
) : AbstractListHandler<Update, CommandRequest>(commandHandlers), UpdateHandler {

    override fun stringify(handler: Handler<CommandRequest>): String {
        val cmd = handler as CommandHandler
        return "Command '${cmd.description()}'(${cmd.javaClass}) registered."
    }

    override fun map(input: Update): CommandRequest? {
        if (input.message?.isCommand != true) {
            return null
        }
        val message = input.message
        val command = CommandRequest.parse(message)
        log.debug { "Command parsed: $command from '${message.text}'" }
        return command
    }
}