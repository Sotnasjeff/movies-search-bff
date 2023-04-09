package com.dataprovider.http.clients.openmoviedatabaseapi.v1

import com.configuration.circuitbreaker.attemptAndRecover
import com.dataprovider.http.clients.openmoviedatabaseapi.v1.configuration.OpenMovieDatabaseApiV1CircuitBreaker
import com.dataprovider.http.clients.openmoviedatabaseapi.v1.entity.OpenMovieDatabaseResponse
import com.dataprovider.http.configuration.HttpClientProperties
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.ObjectMapper
import com.log.LogContextHelper
import com.web.headers.HeaderContextValue
import com.web.headers.Headers
import com.web.headers.HeadersContextHelper
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.serialization.kotlinx.json.json
import io.ktor.util.InternalAPI
import io.ktor.util.toByteArray
import org.slf4j.LoggerFactory

data class OpenMovieDatabaseApiHttpClient(
    private val properties: HttpClientProperties,
    private var objectMapper: ObjectMapper,
    private val resilience: OpenMovieDatabaseApiV1CircuitBreaker
) {
    private var url: String = properties.url

    private var client: HttpClient = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }
        engine {
            maxConnectionsCount = properties.maxConnectionsCount
            endpoint.apply {
                connectAttempts = properties.connectRetryAttempts
                maxConnectionsPerRoute = properties.maxConnectionsPerRoute
                pipelineMaxSize = properties.pipelineMaxSize
                keepAliveTime = properties.keepAliveTime
                connectTimeout = properties.connectTimeout
                requestTimeout = properties.readTimeout
            }
        }
    }

    @OptIn(InternalAPI::class)
    suspend fun getMoviesByTitle(t: String, y: Int, plot: String, type: String?): OpenMovieDatabaseResponse? {
        return resilience.circuitBreaker.attemptAndRecover(
            tryBlock = {
                val response = client.get(url) {
                    header("Content-Type", "application/json")
                    parameter("apiKey", properties.customProperties["apiKey"])
                    parameter("t", t)
                    parameter("y", y)
                    parameter("plot", plot)
                    parameter("type", type)
                    setOpenMovieDatabaseApiAllowedHeaders()
                }
                val result = objectMapper.readValue(response.content.toByteArray(), OpenMovieDatabaseResponse::class.java)
                result
            },
            recoverBlock = {
                log.warn(
                    "[FALLBACK] Could not get movie, responding empty fallback",
                    LogContextHelper.getLogContext().toMap(),
                    it
                )
                null
            }
        )
    }

    private suspend fun HttpRequestBuilder.setOpenMovieDatabaseApiAllowedHeaders() {
        val headers = HeadersContextHelper.getHeadersContext()
        headers.getHeaderValues().filter { isHeaderAllowedToFoward(it) }.forEach { header(it.key, it.value) }
    }

    private fun isHeaderAllowedToFoward(header: HeaderContextValue) = headersAllowedToFoward.contains(header.key)

    companion object {
        val headersAllowedToFoward = listOf(
            Headers.PLATFORM,
            Headers.APP_VERSION,
            Headers.CHANNEL
        )

        private val log = LoggerFactory.getLogger(OpenMovieDatabaseApiHttpClient::class.java)
    }

}