package com.configuration.properties

import io.ktor.server.config.ApplicationConfig

object ApplicationConfigExtension {

    fun ApplicationConfig.getString(fieldName: String): String {
        return propertyOrNull(fieldName)?.getString() ?: ""
    }

    fun ApplicationConfig.getString(fieldName: String, prefix: String): String {
        return propertyOrNull("$prefix.$fieldName")?.getString() ?: ""
    }

    fun ApplicationConfig.getInt(fieldName: String): Int {
        return getString(fieldName).toInt()
    }

    fun ApplicationConfig.getInt(fieldName: String, prefix: String): Int {
        return getString("$prefix.$fieldName").toInt()
    }

    fun ApplicationConfig.getFloat(fieldName: String): Float {
        return getString(fieldName).toFloat()
    }

    fun ApplicationConfig.getFloat(fieldName: String, prefix: String): Float {
        return getString("$prefix.$fieldName").toFloat()
    }

    fun ApplicationConfig.getLong(fieldName: String): Long {
        return getString(fieldName).toLong()
    }

    fun ApplicationConfig.getLong(fieldName: String, prefix: String): Long {
        return getString("$prefix.$fieldName").toLong()
    }
}