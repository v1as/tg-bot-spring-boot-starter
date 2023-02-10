package ru.v1as.tg.starter.update.command

interface CommandHandler {
    fun handle(command: CommandRequest): Handled
    fun description() = ""
}