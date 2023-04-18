package ru.v1as.tg.starter

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestPropertySource
import ru.v1as.tg.starter.configuration.TgBotAutoConfiguration

@SpringBootTest
@ContextConfiguration(classes = [TgBotAutoConfiguration::class])
@TestPropertySource(locations = ["classpath:application.yml"])
class StarterApplicationTests {

    @MockBean
    var ignored: TgBotRunner? = null

    @Autowired
    var bot: TgLongPollingBot? = null

    @Test
    fun botWasStarted() {
        assertEquals("TEST_BOT", bot?.botUsername)
    }

}
