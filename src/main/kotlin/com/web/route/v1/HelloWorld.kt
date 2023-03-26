package com.web.route.v1

import io.ktor.server.application.call
import io.ktor.server.response.respondText
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.kodein.di.Kodein

@ExperimentalCoroutinesApi
fun Routing.helloWorldRoute(kodein: Kodein) {
    get("/hello"){
        call.respondText("Hello World")
    }
}