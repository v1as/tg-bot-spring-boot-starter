package ru.v1as.tg.starter.configuration

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Bean
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestPropertySource
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.api.objects.Update
import ru.v1as.tg.starter.TgBotRunner
import ru.v1as.tg.starter.TgLongPollingBot
import ru.v1as.tg.starter.model.base.TgChatWrapper
import ru.v1as.tg.starter.model.base.TgUserWrapper
import ru.v1as.tg.starter.update.command.AbstractCommandHandler
import ru.v1as.tg.starter.update.command.CommandRequest
import java.util.concurrent.atomic.AtomicBoolean

@SpringBootTest
@ContextConfiguration(classes = [TgBotAutoConfiguration::class])
@TestPropertySource(locations = ["classpath:application.yml"])
class TgCommandsConfigurationTest {


    @MockBean
    var ignored: TgBotRunner? = null

    @Autowired
    var bot: TgLongPollingBot? = null

    val processed = AtomicBoolean()


    @Test
    fun `Command should be processed`() {
        bot?.onUpdateReceived(Update().also { it.message = Message() })
        assertTrue(processed.get())
    }

    companion object {
        @TestConfiguration
        class TestableConfiguration {

            @Bean
            fun command() = object : AbstractCommandHandler("test") {
                override fun handle(
                    command: CommandRequest,
                    user: TgUserWrapper,
                    chat: TgChatWrapper
                ) {
//                this@TgCommandsConfigurationTest.processed.set(true)
                }
            }
        }
    }
}