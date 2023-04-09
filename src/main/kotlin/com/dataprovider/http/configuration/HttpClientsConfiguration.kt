package com.dataprovider.http.configuration

import com.dataprovider.http.clients.openmoviedatabaseapi.v1.configuration.openMovieDatabaseApiV1Configuration
import org.kodein.di.Kodein

val httpClientConfiguration = Kodein.Module("httpClientsConfiguration") {
    import(openMovieDatabaseApiV1Configuration)
}