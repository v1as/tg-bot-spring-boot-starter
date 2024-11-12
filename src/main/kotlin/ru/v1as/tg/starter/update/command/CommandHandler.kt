package ru.v1as.tg.starter.update.command

import ru.operation.handler.Handler

interface CommandHandler : Handler<CommandRequest> {
    fun description() = ""
}