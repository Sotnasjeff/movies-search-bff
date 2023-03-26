package com.web.i18n

import com.typesafe.config.Config
import com.typesafe.config.ConfigException
import com.typesafe.config.ConfigFactory
import io.ktor.server.request.ApplicationRequest
import io.ktor.server.request.acceptLanguage
import java.util.Locale

const val DEFAULT_LOCALE_STRING = "pt_BR"

object I18nService {
    private val i18nConfig: Config = ConfigFactory.load("messages.conf").resolve()

    fun getMessage(request: ApplicationRequest, key: Messages, vararg params: String): String {
        return getMessage(getLocale(request), key, *params)
    }

    fun getMessage(locale: Locale, messages: Messages, vararg  params: String): String{
        return getMessage(locale, messages.key, *params)
    }

    fun getMessage(locale: Locale, message: String, vararg params: String): String {
        if(message.isEmpty()) return ""

        return getMessageWithParams(
            try {
                i18nConfig.getString("$locale.${message}")
            } catch (ex: ConfigException) {
                i18nConfig.getString("$DEFAULT_LOCALE_STRING.${message}")
            },
            *params
        )
    }

    private fun getMessageWithParams(message: String, vararg params: String): String {
        var result = message
        params.forEachIndexed { index, s ->
            result = result.replace("$index", s)
        }
        return result
    }

    fun getLocale(request: ApplicationRequest): Locale {
        return Locale.forLanguageTag(request.acceptLanguage()?: DEFAULT_LOCALE_STRING)
    }
}