package ru.v1as.tg.starter.keyboard

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import ru.v1as.tg.starter.TgBotProperties

class StartCommandLinkFactory(val tgBot: TgBotProperties) {

    fun buildButton(text: String, vararg arguments: Any): InlineKeyboardButton {
        val argumentStr = arguments.joinToString("_")
        return InlineKeyboardButton(text).also {
            it.url = "https://telegram.me/${tgBot.username}?start=$argumentStr"
        }
    }

    fun buildKeyboard(text: String, vararg arguments: Any) =
        InlineKeyboardMarkup(listOf(listOf(buildButton(text, *arguments))))
}