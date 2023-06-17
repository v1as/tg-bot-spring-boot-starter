package ru.v1as.tg.starter.update.command

import mu.KotlinLogging
import org.telegram.telegrambots.meta.api.objects.Update
import ru.v1as.tg.starter.update.UpdateHandler
import ru.v1as.tg.starter.update.handle.Handled
import ru.v1as.tg.starter.update.handle.error
import ru.v1as.tg.starter.update.handle.unmatched
import javax.annotation.PostConstruct

private val log = KotlinLogging.logger {}

class BaseCommandsHandler(
    private val commandHandlers: List<CommandHandler>
) : UpdateHandler {

    @PostConstruct
    fun setup() {
        for (cmd in commandHandlers) {
            log.info("Command '${cmd.description()}'(${cmd.javaClass}) registered.")
        }
    }

    override fun handle(update: Update): Handled {
        if (update.message?.isCommand != true) {
            return unmatched()
        }
        val message = update.message
        val command = CommandRequest.parse(message)
        log.debug { "Command parsed: $command from '${message.text}'" }
        var handled = unmatched()
        for (commandHandler in commandHandlers) {
            try {
                handled = handled.reduce(commandHandler.handle(command))
                if (handled.isDone()) {
                    log.debug { "Command handling ${commandHandler::class.simpleName} with $handled" }
                    return handled
                } else {
                    log.trace { "Command handling ${commandHandler::class.simpleName} with $handled" }
                }
            } catch (ex: Exception) {
                handled = handled.reduce(error(ex))
            }
        }
        return handled
    }
}