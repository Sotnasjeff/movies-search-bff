package com

import com.fasterxml.jackson.databind.ObjectMapper
import com.log.installLogInterceptor
import com.web.headers.installHeaderInterceptors
import com.web.route.v1.helloWorldRoute
import io.ktor.http.ContentType
import io.ktor.serialization.jackson.JacksonConverter
import io.ktor.server.application.Application
import io.ktor.server.application.ApplicationStarted
import io.ktor.server.application.install
import io.ktor.server.application.log
import io.ktor.server.locations.Locations
import io.ktor.server.metrics.micrometer.MicrometerMetrics
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.routing.routing
import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.binder.jvm.ClassLoaderMetrics
import io.micrometer.core.instrument.binder.jvm.JvmMemoryMetrics
import io.micrometer.core.instrument.binder.jvm.JvmThreadMetrics
import io.micrometer.core.instrument.binder.system.FileDescriptorMetrics
import io.micrometer.core.instrument.binder.system.ProcessorMetrics
import io.micrometer.core.instrument.distribution.DistributionStatisticConfig
import org.kodein.di.Kodein
import org.kodein.di.generic.instance

fun Application.applicationRoutesModule(kodein: Kodein) {
    environment.monitor.subscribe(ApplicationStarted) {
        log.info("Service started at port ${environment.config.property("ktor.deployment.port").getString()}")
    }

    install(Locations)

    install(StatusPages) {

    }

    install(ContentNegotiation) {
        val objectMapper: ObjectMapper by kodein.instance()
        register(ContentType.Application.Json, JacksonConverter(objectMapper))
    }

    val metricRegistry: MeterRegistry by kodein.instance()
    install(MicrometerMetrics) {
        registry = metricRegistry
        distributionStatisticConfig = DistributionStatisticConfig.Builder()
            .percentilesHistogram(true)
            .build()
        meterBinders = listOf(
            ClassLoaderMetrics(),
            JvmMemoryMetrics(),
            ProcessorMetrics(),
            JvmThreadMetrics(),
            FileDescriptorMetrics()
        )
    }

    routing {
        installHeaderInterceptors()
        installLogInterceptor()
        helloWorldRoute(kodein)
    }

}