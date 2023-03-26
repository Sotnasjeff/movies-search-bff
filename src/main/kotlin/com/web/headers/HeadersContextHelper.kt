package com.web.headers

import io.ktor.server.request.ApplicationRequest
import io.ktor.util.toMap
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext

object HeadersContextHelper {
    suspend fun getHeadersContext(): HeadersContext {
        return coroutineContext[HeadersCoroutineContextElement]!!.headersContext
    }

    suspend fun hasHeader(header: String): Boolean {
        return getHeadersContext().getHeaderValues().any { it.key.equals(header, ignoreCase = true) }
    }

    fun initFromRequest(request: ApplicationRequest): CoroutineContext {
        val headersContext = getHeadersContextFromRequest(request)
        return HeadersCoroutineContextElement(headersContext)
    }

    private fun getHeadersContextFromRequest(request: ApplicationRequest): HeadersContext {
        return HeadersContext(request.headers.toMap().mapValues { it.value.firstOrNull().orEmpty() })
    }
}