package ru.v1as.tg.starter.update

import org.telegram.telegrambots.meta.api.objects.Update
import ru.v1as.tg.starter.update.exception.UnsupportedUpdateException

@Suppress("UNNECESSARY_SAFE_CALL")
open class BaseUpdateDataExtractor : UpdateDataExtractor {

    override fun userId(update: Update): Long {
        return update?.message?.from?.id
            ?: update?.callbackQuery?.from?.id
            ?: unsupportedUserIdExtraction(update)
    }

    open protected fun unsupportedUserIdExtraction(update: Update): Long {
        throw UnsupportedUpdateException(update)
    }

    override fun chatId(update: Update): Long {
        return update?.message?.chatId
            ?: update?.callbackQuery?.message?.chatId
            ?: unsupportedChatIdExtraction(update)
    }

    open protected fun unsupportedChatIdExtraction(update: Update): Long {
        throw UnsupportedUpdateException(update)
    }
}