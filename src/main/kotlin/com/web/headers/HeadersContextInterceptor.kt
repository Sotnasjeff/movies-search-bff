package com.web.headers

import io.ktor.server.application.ApplicationCallPipeline
import io.ktor.server.application.call
import io.ktor.server.routing.Route
import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.withContext

data class HeadersCoroutineContextElement(val headersContext: HeadersContext) : AbstractCoroutineContextElement(
    HeadersCoroutineContextElement
) {
    companion object Key : CoroutineContext.Key<HeadersCoroutineContextElement>
}

fun Route.installHeaderInterceptors() {
    intercept(ApplicationCallPipeline.Monitoring) {
        withContext(HeadersContextHelper.initFromRequest(call.request)) { proceed() }
    }
}