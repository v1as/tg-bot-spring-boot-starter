package ru.v1as.tg.starter.command

interface CommandHandler {
    fun commandName(): String
    fun handle(commandRequest: CommandRequest)
    fun commandDescription() = ""
}