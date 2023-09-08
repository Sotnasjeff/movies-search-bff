package com.metric

import io.micrometer.core.instrument.DistributionSummary
import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.Tag
import io.micrometer.core.instrument.Timer

class MetricService(val meterRegistry: MeterRegistry) {
    inline fun <reified T> timedGet(
        metricGroup: String,
        metricName: String,
        body: (
            distributionSummary: DistributionSummary.Builder,
        ) -> T
    ): T {
        val sample = Timer.start(meterRegistry)
        val result: T

        val distributionSummaryBuilder = DistributionSummary.builder(metricGroup)
        try {
            result = body(distributionSummaryBuilder)
        } finally {
            sample.stop(meterRegistry.timer(metricName))
            distributionSummaryBuilder.register(meterRegistry).record(1.0)
        }
        return result
    }

    fun registerGauge(label: MetricLabel, value: Double = 1.0) {
        meterRegistry.gauge(label.getName(), value)
    }

    fun incrementCounter(label: MetricLabel, value: Double = 1.0) {
        meterRegistry.counter(label.getName()).increment(value)
    }

    fun incrementCounter(label: MetricLabel, value: Double = 1.0, tags: Map<String, String> = emptyMap()) {
        meterRegistry.counter(label.getName(), tags.map {
            Tag.of(it.key, it.value)
        }).increment(value)
    }

    fun registerHistogram(label: MetricLabel, value: Double = 1.0, maxExpectedValue:Double, tags: Map<String, String> = emptyMap()) {
        DistributionSummary.builder(label.getName()).apply {
            maximumExpectedValue(maxExpectedValue)
            tags(tags.map {
                Tag.of(it.key, it.value)
            })
            publishPercentileHistogram()
        }.register(meterRegistry).record(value)
    }
}