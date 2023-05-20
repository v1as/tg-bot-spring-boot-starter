package ru.v1as.tg.starter.update.command

import org.telegram.telegrambots.meta.api.objects.Message
import ru.v1as.tg.starter.model.TgUser
import ru.v1as.tg.starter.model.base.TgUserWrapper
import java.util.regex.Matcher
import java.util.regex.Pattern

private val PATTERN_WITH_NAME = Pattern.compile("/([\\w\\d_]+)@?([\\w\\d_]+)?(\\s)?(.*)")

data class CommandRequest(
    val message: Message,
    val name: String,
    val botName: String = "",
    val arguments: List<String> = emptyList()
) {
    val from: TgUser = TgUserWrapper(message.from)

    companion object {
        fun parse(message: Message, pattern: Pattern = PATTERN_WITH_NAME): CommandRequest {
            val text = message.text ?: ""
            val matcher: Matcher = pattern.matcher(text)
            require(matcher.matches()) { "Unsupported command format: $text " }
            val name = matcher.group(1)
            val botName = matcher.group(2) ?: ""
            val arguments: List<String> =
                matcher.group(4).split(' ', ';', '_').stream().map { it.trim() }
                    .filter { it.isNotEmpty() }
                    .toList()
            return CommandRequest(message, name, botName, arguments)
        }
    }

    override fun toString(): String {
        return "CommandRequest(msg='${message.messageId}, from='${from.usernameOrFullName()}', 'name='$name', botName='$botName', arguments=$arguments)"
    }
}