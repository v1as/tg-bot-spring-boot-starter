package ru.v1as.tg.starter.update.command

enum class Handled {
    Skipped, Error, Done;

    fun finish(): Boolean {
        return Done == this
    }
}