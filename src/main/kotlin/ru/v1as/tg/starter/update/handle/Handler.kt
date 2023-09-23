package ru.v1as.tg.starter.update.handle

interface Handler<T> {

    fun handle(input: T): Handled
}