package com.log

import com.web.headers.Headers
import io.ktor.server.request.ApplicationRequest
import io.ktor.server.request.header
import kotlin.coroutines.coroutineContext

object LogContextHelper {
    suspend fun getLogContext(): LogContext {
        return coroutineContext[LogCoroutineContextElement.Key]!!.logContext
    }

    fun initFromRequest(request: ApplicationRequest, initBlock: (LogContext) -> Unit = {}): LogCoroutineContextElement {
        val logContext = LogContext()
        logContext.setAccountId(request.header(Headers.ACCOUNT_ID))
        logContext.setRequestId(request.header(Headers.X_CATALOG_REQUEST_ID))
        logContext.setSessionId(request.header(Headers.X_CATALOG_SESSION_ID))

        logContext.putOnContext(Headers.PLATFORM, request.header(Headers.PLATFORM))
        logContext.putOnContext(Headers.APP_VERSION, request.header(Headers.APP_VERSION))

        initBlock(logContext)
        return LogCoroutineContextElement(logContext)
    }

    suspend fun updateLogContext(updateBlock: (LogContext) -> Unit): LogCoroutineContextElement {
        val logContext = coroutineContext[LogCoroutineContextElement.Key]!!.logContext
        updateBlock(logContext)
        return LogCoroutineContextElement(logContext)
    }
}