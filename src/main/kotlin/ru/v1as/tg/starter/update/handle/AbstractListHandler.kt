package ru.v1as.tg.starter.update.handle

import mu.KLoggable
import javax.annotation.PostConstruct


abstract class AbstractListHandler<T, I>(private val handlers: List<Handler<I>>) : Handler<T>, KLoggable {

    override val logger = logger()

    @PostConstruct
    fun setup() {
        for (handler in handlers) {
            logger.info(stringify(handler))
        }
    }

    open fun stringify(handler: Handler<I>) =
        "Handler (${handler.javaClass}) registered."

    private fun handleItem(item: I): Handled {
        var handled = unmatched()
        for (callbackHandlers in handlers) {
            try {
                handled = handled.reduce(callbackHandlers.handle(item))
                if (handled.isDone()) {
                    logger.debug { "Command handling ${callbackHandlers::class.simpleName} with $handled" }
                    return handled
                } else {
                    logger.trace { "Command handling ${callbackHandlers::class.simpleName} with $handled" }
                }
            } catch (ex: Exception) {
                handled = handled.reduce(error(ex))
            }
        }
        return handled
    }

    override fun handle(input: T): Handled {
        val mappedInput: I? = map(input)
        return mappedInput?.let { handleItem(it) } ?: unmatched()
    }

    abstract fun map(input: T): I?

}