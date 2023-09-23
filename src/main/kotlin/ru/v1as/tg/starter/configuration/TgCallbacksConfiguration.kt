package ru.v1as.tg.starter.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.v1as.tg.starter.update.callback.BaseCallbacksHandler
import ru.v1as.tg.starter.update.callback.CallbackHandler

@Configuration
class TgCallbacksConfiguration {

    @Bean
    fun baseCallbacksHandler(callbackHandlers: List<CallbackHandler>) =
        BaseCallbacksHandler(callbackHandlers)

}