package io.lhysin.r2dbc.config

import org.h2.tools.Server
import org.springframework.context.annotation.Configuration
import org.springframework.context.event.ContextClosedEvent
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.context.event.EventListener
import java.sql.SQLException

@Configuration
class H2Console {
    private var webServer: Server? = null
    @EventListener(ContextRefreshedEvent::class)
    @Throws(SQLException::class)
    fun start() {
        webServer = Server.createWebServer("-webPort", "8078", "-tcpAllowOthers").start()
    }

    @EventListener(ContextClosedEvent::class)
    fun stop() {
        webServer!!.stop()
    }
}