package com.dataprovider.http.clients.openmoviedatabaseapi.v1.properties

import com.configuration.circuitbreaker.CircuitBreakerProperties
import com.configuration.properties.ApplicationConfigExtension.getFloat
import com.configuration.properties.ApplicationConfigExtension.getInt
import com.configuration.properties.ApplicationConfigExtension.getLong
import com.configuration.properties.ApplicationConfigExtension.getString
import io.ktor.server.config.ApplicationConfig

fun openMovieDatabaseApiV1ResilienceProperties(config: ApplicationConfig): OpenMovieDatabaseApiResilienceProperties {
    return OpenMovieDatabaseApiResilienceProperties(
        circuitBreakerProperties = CircuitBreakerProperties(
            circuitName = config.getString("openMovieDatabaseApi.resilience.circuitBreaker.circuitName"),
            failureRateThreshold = config.getFloat("openMovieDatabaseApi.resilience.circuitBreaker.failureRateThreshold"),
            waitDurationInOpenStateInSeconds = config.getLong("openMovieDatabaseApi.resilience.circuitBreaker.waitDurationInOpenStateInSeconds"),
            minimumNumberOfCalls = config.getInt("openMovieDatabaseApi.resilience.circuitBreaker.minimumNumberOfCalls")
        )
    )
}

data class OpenMovieDatabaseApiResilienceProperties(
    val circuitBreakerProperties: CircuitBreakerProperties
)