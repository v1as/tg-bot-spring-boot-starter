package ru.v1as.tg.starter.update

import org.telegram.telegrambots.meta.api.objects.Update
import ru.v1as.tg.starter.model.TgChat
import ru.v1as.tg.starter.model.TgUser
import ru.v1as.tg.starter.model.base.TgChatWrapper
import ru.v1as.tg.starter.model.base.TgUserWrapper
import ru.v1as.tg.starter.update.exception.UnsupportedUpdateException

open class BaseUpdateDataExtractor : UpdateDataExtractor {

    override fun user(update: Update): TgUser {
        return update.message?.from?.let { TgUserWrapper(it) }
            ?: update.callbackQuery?.from?.let { TgUserWrapper(it) }
            ?: unsupportedUserIdExtraction(update)
    }

    protected open fun unsupportedUserIdExtraction(update: Update): TgUser {
        throw UnsupportedUpdateException(update, "Unsupported userId extraction")
    }

    override fun chat(update: Update): TgChat {
        return update.message?.chat?.let { TgChatWrapper(it) }
            ?: update.callbackQuery?.message?.chat?.let { TgChatWrapper(it) }
            ?: unsupportedChatIdExtraction(update)
    }

    protected open fun unsupportedChatIdExtraction(update: Update): TgChat {
        throw UnsupportedUpdateException(update, "Unsupported chatId extraction")
    }
}