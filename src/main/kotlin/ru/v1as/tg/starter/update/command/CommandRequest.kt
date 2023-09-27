package ru.v1as.tg.starter.update.command

import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.api.objects.Update
import ru.v1as.tg.starter.model.base.TgChatWrapper
import ru.v1as.tg.starter.model.base.TgUserWrapper
import ru.v1as.tg.starter.update.UpdateRequest
import java.util.regex.Matcher
import java.util.regex.Pattern

private val PATTERN_WITH_NAME = Pattern.compile("/([\\w\\d_]+)@?([\\w\\d_]+)?(\\s)?(.*)")

data class CommandRequest(
    override val update: Update,
    val name: String,
    val botName: String = "",
    val argumentsString: String = "",
    val arguments: List<String> = emptyList(),
) : UpdateRequest(update, TgUserWrapper(update.message.from), TgChatWrapper(update.message.chat)) {
    val message: Message = update.message

    companion object {
        fun parse(update: Update, pattern: Pattern = PATTERN_WITH_NAME): CommandRequest {
            val text = update.message?.text ?: ""
            val matcher: Matcher = pattern.matcher(text)
            require(matcher.matches()) { "Unsupported command format: $text " }
            val name = matcher.group(1)
            val botName = matcher.group(2) ?: ""
            val argumentsString = matcher
                .group(4)
            val arguments: List<String> =
                argumentsString
                    .split(' ', ';', '_', ',')
                    .stream()
                    .map { it.trim() }
                    .filter { it.isNotEmpty() }
                    .toList()
            return CommandRequest(update, name, botName, argumentsString, arguments)
        }
    }

    fun argumentAfter(name: String): String {
        val argNameIndex = arguments.indexOf(name)
        if (argNameIndex >= 0 && arguments.size > argNameIndex + 1) {
            return arguments[argNameIndex + 1]
        }
        throw IllegalStateException("No argument after $name for command: $this")
    }

    override fun toString(): String {
        return "CommandRequest(msg='${message.messageId}, from='${from.usernameOrFullName()}', 'name='$name', botName='$botName', arguments=$arguments)"
    }
}