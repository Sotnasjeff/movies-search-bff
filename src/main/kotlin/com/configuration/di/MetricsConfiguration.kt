package com.configuration.di

import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.composite.CompositeMeterRegistry
import io.micrometer.prometheus.PrometheusConfig
import io.micrometer.prometheus.PrometheusMeterRegistry
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton

val metricsConfiguration = Kodein.Module("metricsConfiguration") {
    val micrometerRegistry = CompositeMeterRegistry()
    val prometheusMeterRegistry = prometheusRegistryConfig(micrometerRegistry)
    bind() from singleton { prometheusMeterRegistry }
    bind<MeterRegistry>() with singleton { micrometerRegistry }
}

fun prometheusRegistryConfig(compositeMeterRegistry: CompositeMeterRegistry): PrometheusMeterRegistry {
    val prometheusMeterRegistry = PrometheusMeterRegistry(PrometheusConfig.DEFAULT)
    compositeMeterRegistry.add(prometheusMeterRegistry)
    return prometheusMeterRegistry
}