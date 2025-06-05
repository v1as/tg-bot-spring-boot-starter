package ru.v1as.tg.starter

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.bean.override.mockito.MockitoBean
import ru.v1as.tg.starter.configuration.TgBotAutoConfiguration
import ru.v1as.tg.starter.configuration.properties.TgBotProperties

@SpringBootTest
@ContextConfiguration(classes = [TgBotAutoConfiguration::class])
@TestPropertySource(locations = ["classpath:application.yml"])
class StarterApplicationTests {

    @MockitoBean
    var ignored: TgBotRunner? = null

    @Autowired
    var props: TgBotProperties? = null

    @Test
    fun botWasStarted() {
        assertEquals("TEST_BOT", props?.username)
    }

}
