package com.configuration.di

import com.core.v1.repository.MovieSearchRepository
import com.dataprovider.http.clients.openmoviedatabaseapi.v1.OpenMovieDatabaseApiSearchRepository
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

val repositoryConfiguration = Kodein.Module(name = "repositoryConfiguration") {
    bind<MovieSearchRepository>() with singleton { OpenMovieDatabaseApiSearchRepository(instance()) }
}