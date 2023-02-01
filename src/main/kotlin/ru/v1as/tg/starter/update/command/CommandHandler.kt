package ru.v1as.tg.starter.update.command

import ru.v1as.tg.starter.update.handle.Handled

interface CommandHandler {
    fun handle(command: CommandRequest): Handled
    fun description() = ""
}