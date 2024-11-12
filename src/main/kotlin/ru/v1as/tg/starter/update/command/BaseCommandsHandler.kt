package ru.v1as.tg.starter.update.command

import mu.KotlinLogging
import org.telegram.telegrambots.meta.api.objects.Update
import ru.operation.handler.Handler
import ru.operation.handler.impl.list.HandlerList
import ru.v1as.tg.starter.update.UpdateHandler

private val log = KotlinLogging.logger {}

class BaseCommandsHandler(
    commandHandlers: List<CommandHandler>
) : UpdateHandler {

    private val commandHandlers = HandlerList(commandHandlers)

    fun stringify(handler: Handler<CommandRequest>): String {
        val cmd = handler as CommandHandler
        return "Command '${cmd.description()}'(${cmd.javaClass}) registered."
    }

    fun map(input: Update): CommandRequest? {
        if (input.message?.isCommand != true) {
            return null
        }
        val command = CommandRequest.parse(input)
        log.debug { "Command parsed: $command from '${input.message.text}'" }
        return command
    }

    override fun handle(update: Update) = commandHandlers.handle(map(update))
}