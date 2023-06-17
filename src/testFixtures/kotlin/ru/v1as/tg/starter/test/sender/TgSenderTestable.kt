package ru.v1as.tg.starter.test.sender

import java.io.Serializable
import java.util.*
import java.util.concurrent.CompletableFuture
import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import ru.v1as.tg.starter.TgSender

class TgSenderTestable(
    private val methodHandlers:
        List<TgSenderMethodHandler<out Serializable, out BotApiMethod<out Serializable>>>
) : TgSender {

    private val executedMethods:
        MutableList<TgMethodExecuted<out Serializable, out BotApiMethod<out Serializable>>> =
        mutableListOf()

    override fun <T : Serializable, Method : BotApiMethod<T>> execute(method: Method): T {
        val methodResult =
            methodHandlers
                .stream()
                .map { it.execute(method as Nothing) }
                .filter { it != null }
                .findFirst()
                .orElseThrow { UnsupportedOperationException() }
        executedMethods.add(TgMethodExecuted(method, methodResult as T))
        return methodResult
    }

    override fun <T : Serializable, Method : BotApiMethod<T>> executeAsync(
        method: Method
    ): CompletableFuture<T> = CompletableFuture.completedFuture(execute(method))

    fun <Method : BotApiMethod<out Serializable>> poll(
        match: (method: Method) -> Boolean
    ): Optional<TgMethodExecuted<out Serializable, out BotApiMethod<out Serializable>>> {
        return executedMethods.stream().filter { match(it.method as Method) }.findFirst()
    }
}
