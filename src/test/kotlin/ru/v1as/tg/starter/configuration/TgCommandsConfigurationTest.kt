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
import ru.v1as.tg.starter.TgBotRunner
import ru.v1as.tg.starter.TgLongPollingBot
import ru.v1as.tg.starter.model.base.TgChatWrapper
import ru.v1as.tg.starter.model.base.TgUserWrapper
import ru.v1as.tg.starter.update.command.AbstractCommandHandler
import ru.v1as.tg.starter.update.command.CommandRequest
import ru.v1as.tg.starter.update.messageUpdate
import java.util.concurrent.atomic.AtomicBoolean

@SpringBootTest
@ContextConfiguration(classes = [TgBotAutoConfiguration::class, TgCommandsConfigurationTest.Companion.TestableConfiguration::class])
@TestPropertySource(locations = ["classpath:application.yml"])
class TgCommandsConfigurationTest {


    @MockBean
    var ignored: TgBotRunner? = null

    @Autowired
    var bot: TgLongPollingBot? = null

    @Autowired
    var testableConfiguration: TestableConfiguration? = null


    @Test
    fun `Command should be processed`() {
        bot?.onUpdateReceived(messageUpdate(text = "/test"))
        assertTrue(testableConfiguration!!.processed.get())
    }

    companion object {
        @TestConfiguration
        class TestableConfiguration {
            val processed = AtomicBoolean()

            @Bean
            fun command() = object : AbstractCommandHandler("test") {
                override fun handle(
                    command: CommandRequest,
                    user: TgUserWrapper,
                    chat: TgChatWrapper
                ) {
                    processed.set(true)
                }
            }
        }
    }
}