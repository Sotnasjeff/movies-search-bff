package com.configuration.di

import com.web.i18n.I18nService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton

@ExperimentalCoroutinesApi
val serviceConfiguration = Kodein.Module(name = "serviceConfiguration") {
    bind() from singleton { I18nService }
}