package ru.v1as.tg.starter.command

import org.telegram.telegrambots.meta.api.objects.Update
import ru.v1as.tg.starter.update.UpdateHandler

class BaseCommandsHandler(commandHandlers: List<CommandHandler>) : UpdateHandler {
    private val commandHandlers: Map<String, CommandHandler>

    init {
        this.commandHandlers = commandHandlers.associateBy { it.commandName() }
    }

    override fun handle(update: Update): Boolean {
        if (update.message?.isCommand != true) {
            return false
        }
        val command = CommandRequest.parse(update.message)
        if (commandHandlers.contains(command.name)) {
            commandHandlers[command.name]!!.handle(command)
            return true
        }
        return false
    }
}