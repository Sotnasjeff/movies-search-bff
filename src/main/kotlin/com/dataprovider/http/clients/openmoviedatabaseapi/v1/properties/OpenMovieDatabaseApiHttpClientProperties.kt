package com.dataprovider.http.clients.openmoviedatabaseapi.v1.properties

import com.configuration.properties.ApplicationConfigExtension.getInt
import com.configuration.properties.ApplicationConfigExtension.getLong
import com.configuration.properties.ApplicationConfigExtension.getString
import com.dataprovider.http.configuration.HttpClientProperties
import io.ktor.server.config.ApplicationConfig

fun openMovieDatabaseApiV1HttpClientProperties(config: ApplicationConfig): HttpClientProperties {
    return HttpClientProperties(
        url = config.getString("openMovieDatabaseApi.httpClient.url"),
        customProperties = mapOf(
            "apiKey" to config.getString("openMovieDatabaseApi.httpClient.apiKey")
        ),
        maxConnectionsCount = config.getInt("openMovieDatabaseApi.httpClient.maxConnectionsCount"),
        maxConnectionsPerRoute = config.getInt("openMovieDatabaseApi.httpClient.maxConnectionsPerRoute"),
        keepAliveTime = config.getLong("openMovieDatabaseApi.httpClient.keepAliveTime"),
        connectTimeout = config.getLong("openMovieDatabaseApi.httpClient.connectTimeout"),
        readTimeout = config.getLong("openMovieDatabaseApi.httpClient.readTimeout"),
        connectRetryAttempts = config.getInt("openMovieDatabaseApi.httpClient.connectRetryAttempts")
    )
}