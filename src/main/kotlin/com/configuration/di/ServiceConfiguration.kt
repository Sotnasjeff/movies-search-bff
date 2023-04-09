package com.configuration.di

import com.core.v1.service.MovieSearchService
import com.web.i18n.I18nService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

@ExperimentalCoroutinesApi
val serviceConfiguration = Kodein.Module(name = "serviceConfiguration") {
    bind() from singleton { I18nService }
    bind() from singleton { MovieSearchService(instance()) }
}