package com.configuration.circuitbreaker

data class CircuitBreakerProperties(
    val circuitName: String,
    val failureRateThreshold: Float,
    val waitDurationInOpenStateInSeconds: Long,
    val minimumNumberOfCalls: Int,
)