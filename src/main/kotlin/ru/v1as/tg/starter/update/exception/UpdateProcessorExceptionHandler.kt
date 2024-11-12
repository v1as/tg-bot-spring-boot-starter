package ru.v1as.tg.starter.update.exception

import org.telegram.telegrambots.meta.api.objects.Update
import ru.operation.Failed
import ru.operation.handler.Handler

interface UpdateProcessorExceptionHandler : Handler<Failed<Update>>