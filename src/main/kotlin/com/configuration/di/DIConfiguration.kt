package com.configuration.di

import com.configuration.circuitbreaker.resilienceConfiguration
import com.configuration.json.configureObjectMapper
import com.configuration.json.objectMapperConfiguration
import com.dataprovider.http.configuration.httpClientConfiguration
import com.typesafe.config.ConfigFactory
import io.ktor.server.config.HoconApplicationConfig
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

@ExperimentalCoroutinesApi
val configurationModule = Kodein.Module("configuration") {
    bind<HoconApplicationConfig>() with singleton { HoconApplicationConfig(ConfigFactory.load()) }
    bind() from instance(configureObjectMapper())
    bind() from instance(objectMapperConfiguration())
    import(serviceConfiguration)
    import(metricsConfiguration)
    import(resilienceConfiguration)
    import(httpClientConfiguration)
    import(repositoryConfiguration)

}