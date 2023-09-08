package com.metric

enum class MetricLabel {
    MOVIES_FOUND,
    MOVIES_NOT_FOUND,
    CLIENT_RESPONSE_TIME;

    fun getName():String {
        return "custom_${this.toString().lowercase()}"
    }
}