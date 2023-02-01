package ru.v1as.tg.starter.update.handle

fun unmatched() = Handled(HandledType.UNMATCHED)

fun handled() = Handled(HandledType.HANDLED)

fun handledWithError(errors: List<Exception>) = Handled(HandledType.HANDLED, errors)

fun error(ex: Exception) = Handled(HandledType.ERROR, listOf(ex))

data class Handled(val type: HandledType, val errors: List<Exception> = emptyList()) {
    fun reduce(other: Handled): Handled {
        return Handled(if (type > other.type) type else other.type, errors + other.errors)
    }

    fun isDone() = type == HandledType.HANDLED
}