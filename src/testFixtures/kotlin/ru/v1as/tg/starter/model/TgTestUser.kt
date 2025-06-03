package ru.v1as.tg.starter.model

import org.telegram.telegrambots.meta.api.objects.User

class TgTestUser(
    private val id: Long,
    private val userName: String,
    private val firstName: String = "",
    private val lastName: String = "",
    private val languageCode: String = "",
) : TgUser {

    override fun id(): Long = id

    override fun userName() = userName

    override fun firstName(): String = firstName

    override fun lastName(): String = lastName

    override fun languageCode(): String = languageCode

    override fun model(): User {
        val user = User(id(), firstName(), false)
        user.userName = userName()
        user.lastName = lastName()
        user.languageCode = languageCode()
        return user
    }
}