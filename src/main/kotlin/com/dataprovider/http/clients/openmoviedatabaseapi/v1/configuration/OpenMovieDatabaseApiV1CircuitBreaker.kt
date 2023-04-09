package com.dataprovider.http.clients.openmoviedatabaseapi.v1.configuration

import com.dataprovider.http.clients.openmoviedatabaseapi.v1.properties.OpenMovieDatabaseApiResilienceProperties
import io.github.resilience4j.circuitbreaker.CircuitBreaker
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry
import java.time.Duration

class OpenMovieDatabaseApiV1CircuitBreaker(
    properties: OpenMovieDatabaseApiResilienceProperties,
    circuitBreakerRegistry: CircuitBreakerRegistry
) {
    private val circuitBreakerProperties = properties.circuitBreakerProperties

    val circuitBreaker: CircuitBreaker = circuitBreakerRegistry.circuitBreaker(
        circuitBreakerProperties.circuitName,
        CircuitBreakerConfig.custom()
            .failureRateThreshold(circuitBreakerProperties.failureRateThreshold)
            .waitDurationInOpenState(Duration.ofSeconds(circuitBreakerProperties.waitDurationInOpenStateInSeconds))
            .slidingWindow(
                10,
                circuitBreakerProperties.minimumNumberOfCalls,
                CircuitBreakerConfig.SlidingWindowType.COUNT_BASED
            )
            .build()
    )
}
