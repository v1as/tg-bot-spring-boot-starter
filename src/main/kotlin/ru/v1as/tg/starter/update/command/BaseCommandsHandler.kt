package ru.v1as.tg.starter.update.command

import mu.KotlinLogging
import org.telegram.telegrambots.meta.api.objects.Update
import ru.v1as.tg.starter.update.UpdateHandler

private val log = KotlinLogging.logger {}

class BaseCommandsHandler(private val commandHandlers: List<CommandHandler>) : UpdateHandler {

    override fun handle(update: Update): Boolean {
        if (update.message?.isCommand != true) {
            return false
        }
        val command = CommandRequest.parse(update.message)
        log.debug { "Command parsed: $command" }
        for (commandHandler in commandHandlers) {
            try {
                val handled = commandHandler.handle(command)
                log.debug { "Command handling ${commandHandler::class.simpleName} with $handled" }
                if (handled.finish()) {
                    return true
                }
            } catch (ex: Exception) {
                log.error(ex) { "Error while command $command handling" }
            }
        }
        return false
    }
}