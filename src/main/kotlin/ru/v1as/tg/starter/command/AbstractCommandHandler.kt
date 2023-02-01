package ru.v1as.tg.starter.command

abstract class AbstractCommandHandler(
    val commandName: String,
    val commandDescription: String = ""
) :
    CommandHandler+ {

}