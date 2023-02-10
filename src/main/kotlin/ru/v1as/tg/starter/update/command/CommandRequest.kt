package ru.v1as.tg.starter.update.command

import org.telegram.telegrambots.meta.api.objects.Message
import java.util.regex.Matcher
import java.util.regex.Pattern

private val PATTERN_WITH_NAME = Pattern.compile("/([\\w\\d_]+)@?([\\w\\d_]+)?(\\s)?(.*)")

data class CommandRequest(
    val message: Message,
    val name: String,
    val botName: String = "",
    val arguments: List<String> = emptyList()
) {
    companion object {
        fun parse(message: Message, pattern: Pattern = PATTERN_WITH_NAME): CommandRequest {
            val text = message.text ?: ""
            val matcher: Matcher = pattern.matcher(text)
            require(matcher.matches()) { "Unsupported command format: $text " }
            val name = matcher.group(1)
            val botName = matcher.group(2) ?: ""
            val arguments: List<String> =
                matcher.group(4).split(" ").stream().map { it.trim() }.filter { it.isNotEmpty() }
                    .toList()
            return CommandRequest(message, name, botName, arguments)
        }
    }
}