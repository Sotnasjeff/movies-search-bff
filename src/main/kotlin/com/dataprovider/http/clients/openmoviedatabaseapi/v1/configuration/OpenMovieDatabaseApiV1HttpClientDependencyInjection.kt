package com.dataprovider.http.clients.openmoviedatabaseapi.v1.configuration

import com.dataprovider.http.clients.openmoviedatabaseapi.v1.OpenMovieDatabaseApiHttpClient
import com.dataprovider.http.clients.openmoviedatabaseapi.v1.properties.openMovieDatabaseApiV1HttpClientProperties
import com.dataprovider.http.clients.openmoviedatabaseapi.v1.properties.openMovieDatabaseApiV1ResilienceProperties
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

var openMovieDatabaseApiV1Configuration = Kodein.Module("httpClientsConfiguration.openMovieDatabase") {
    bind() from singleton { openMovieDatabaseApiV1ResilienceProperties(instance()) }
    bind() from singleton { OpenMovieDatabaseApiV1CircuitBreaker(instance(), instance()) }
    bind() from singleton { openMovieDatabaseApiV1HttpClientProperties(instance()) }
    bind() from singleton { OpenMovieDatabaseApiHttpClient(instance(), instance(), instance()) }
}