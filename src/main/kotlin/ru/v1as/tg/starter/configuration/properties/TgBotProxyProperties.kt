package ru.v1as.tg.starter.configuration.properties

import java.net.Proxy

class TgBotProxyProperties(
    var host: String = "",
    var port: Int = -1,
    var username: String = "",
    var password: String = "",
    var type: Proxy.Type = Proxy.Type.DIRECT
)