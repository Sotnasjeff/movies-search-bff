package com

import com.fasterxml.jackson.databind.ObjectMapper
import com.web.route.monitor.monitor
import io.ktor.http.ContentType
import io.ktor.serialization.jackson.JacksonConverter
import io.ktor.server.application.Application
import io.ktor.server.application.ApplicationStarted
import io.ktor.server.application.install
import io.ktor.server.application.log
import io.ktor.server.plugins.autohead.AutoHeadResponse
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.routing.routing
import org.kodein.di.Kodein
import org.kodein.di.generic.instance

fun Application.monitorRoutesModule(kodein: Kodein) {
    environment.monitor.subscribe(ApplicationStarted) {
        log.info("Management services started at port ${
            environment.config.property("ktor.management.port").getString()
        }")
    }
    install(ContentNegotiation) {
        val objectMapper: ObjectMapper by kodein.instance()
        register(ContentType.Application.Json, JacksonConverter(objectMapper))
    }
    install(AutoHeadResponse)
    routing {
        monitor(kodein)
    }
}