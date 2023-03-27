package com.configuration.circuitbreaker

import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry
import io.github.resilience4j.micrometer.tagged.TaggedCircuitBreakerMetrics
import io.micrometer.core.instrument.MeterRegistry
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

val resilienceConfiguration = Kodein.Module("resilienceConfiguration") {
    bind() from singleton { createCircuitBreakerRegistry(instance()) }
}

private fun createCircuitBreakerRegistry(meterRegistry: MeterRegistry): CircuitBreakerRegistry {
    val registry = CircuitBreakerRegistry.ofDefaults()
    TaggedCircuitBreakerMetrics.ofCircuitBreakerRegistry(registry).bindTo(meterRegistry)
    return registry
}