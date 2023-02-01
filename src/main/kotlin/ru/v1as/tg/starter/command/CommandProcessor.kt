package ru.v1as.tg.starter.command

import ru.v1as.tg.starter.model.TgChat
import ru.v1as.tg.starter.model.TgUser

interface CommandProcessor {
    fun register(commandHandler: CommandHandler)
    fun process(commandRequest: CommandRequest, tgChat: TgChat, tgUser: TgUser)
}