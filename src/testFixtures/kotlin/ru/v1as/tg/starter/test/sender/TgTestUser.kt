package ru.v1as.tg.starter.test.sender

import java.util.concurrent.atomic.AtomicLong
import org.telegram.telegrambots.meta.api.objects.User
import ru.v1as.tg.starter.model.TgUser

private val userIdSerial = AtomicLong()

class TgTestUser(private val username: String) : TgUser {

    private val userId = userIdSerial.incrementAndGet()

    override fun id() = userId

    override fun userName() = username

    override fun firstName(): String {
        TODO("Not yet implemented")
    }

    override fun lastName(): String {
        TODO("Not yet implemented")
    }

    override fun languageCode(): String {
        TODO("Not yet implemented")
    }

    override fun model(): User {
        TODO("Not yet implemented")
    }
}
