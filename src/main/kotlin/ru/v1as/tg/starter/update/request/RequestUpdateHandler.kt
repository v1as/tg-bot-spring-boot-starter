package ru.v1as.tg.starter.update.request

import ru.v1as.tg.starter.update.UpdateHandler

interface RequestUpdateHandler : UpdateHandler {

    fun register(request: UpdateRequest)

}