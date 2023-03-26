package com.web.route.monitor

import com.typesafe.config.ConfigFactory
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.micrometer.prometheus.PrometheusMeterRegistry
import org.kodein.di.Kodein
import org.kodein.di.generic.instance

private val passwordRegex = """password""".toRegex()
private const val REPLACEMENT_STRING = "******"

fun Route.monitor(kodein: Kodein) {
    val prometheusMeterRegistry: PrometheusMeterRegistry by kodein.instance()
    get("/health") {
        call.respond(HttpStatusCode.OK)
    }

    get("/env") {
        val response = ConfigFactory.load().resolve().entrySet().associate { (key, value) ->
            val configValue = if (key.contains(passwordRegex)) REPLACEMENT_STRING else value.unwrapped().toString()
            key to ConfigItem(configValue, value.origin().description())
        }
        call.respond(response)
    }

    get("/prometheus") {
        val scrape = prometheusMeterRegistry.scrape()
        call.respondText(status = HttpStatusCode.OK, text = scrape)
    }
}

private data class ConfigItem(val value: String, val origin: String)