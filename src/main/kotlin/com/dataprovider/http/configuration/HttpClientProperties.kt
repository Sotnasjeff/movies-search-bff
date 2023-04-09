package com.dataprovider.http.configuration

data class HttpClientProperties(
    val url: String,
    val customProperties: Map<String, String> = mapOf(),
    val maxConnectionsCount: Int,
    val maxConnectionsPerRoute: Int,
    var keepAliveTime: Long = 5000,
    var connectTimeout: Long = 300,
    var readTimeout: Long = 1000,
    var connectRetryAttempts: Int,
    var pipelineMaxSize: Int = 10000
)