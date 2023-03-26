package com.log

import com.log.LogContextHelper.initFromRequest
import io.ktor.server.application.ApplicationCallPipeline
import io.ktor.server.application.call
import io.ktor.server.routing.Route
import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.withContext

data class LogCoroutineContextElement(val logContext: LogContext): AbstractCoroutineContextElement(LogCoroutineContextElement) {
    companion object Key : CoroutineContext.Key<LogCoroutineContextElement>
}

fun Route.installLogInterceptor() {
    intercept(ApplicationCallPipeline.Monitoring) {
        withContext(initFromRequest(call.request)) { proceed()}
    }
}