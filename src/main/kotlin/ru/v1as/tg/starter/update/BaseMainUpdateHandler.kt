package ru.v1as.tg.starter.update

import org.telegram.telegrambots.meta.api.objects.Update
import ru.operation.handler.Handler
import ru.operation.handler.impl.CompositeHandler
import ru.operation.handler.impl.LoggingExceptionHandler
import ru.operation.handler.impl.list.HandlerList
import ru.operation.handler.impl.list.HandlerModifier.CONTINUE_ON_DONE
import ru.v1as.tg.starter.update.exception.UpdateProcessorExceptionHandler

open class BaseMainUpdateHandler(
    beforeUpdateHandlers: List<BeforeUpdateHandler>,
    updateHandlers: List<UpdateHandler>,
    updateProcessorExceptionHandler: UpdateProcessorExceptionHandler,
    unmatchedUpdateHandler: UnmatchedUpdateHandler,
    afterUpdateHandlers: List<AfterUpdateHandler>,
) : CompositeHandler<Update>(
    HandlerList(
        beforeUpdateHandlers.map { h -> Handler<Update> { h.beforeHandle(it) } },
        setOf(CONTINUE_ON_DONE),
        updateProcessorExceptionHandler
    ),
    HandlerList(
        listOf(
            HandlerList(updateHandlers, setOf(), updateProcessorExceptionHandler),
            unmatchedUpdateHandler
        ), setOf(), LoggingExceptionHandler()
    ),
    HandlerList(
        afterUpdateHandlers.map { h -> Handler<Update> { h.afterHandle(it) } },
        setOf(CONTINUE_ON_DONE),
        updateProcessorExceptionHandler
    )
), MainUpdateHandler