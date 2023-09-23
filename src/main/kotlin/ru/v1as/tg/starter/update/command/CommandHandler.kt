package ru.v1as.tg.starter.update.command

import ru.v1as.tg.starter.update.handle.Handler

interface CommandHandler : Handler<CommandRequest> {
    fun description() = ""
}