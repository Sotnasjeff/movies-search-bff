package com.configuration.json

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.time.ZonedDateTime

fun configureObjectMapper(): ObjectMapper {
    val objectMapper = jacksonObjectMapper()
    objectMapper.apply(objectMapperConfiguration())
    return objectMapper
}

fun objectMapperConfiguration(): ObjectMapper.() -> Unit {
    return {
        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
        configure(SerializationFeature.INDENT_OUTPUT, false)
        configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true)

        setSerializationInclusion(JsonInclude.Include.NON_EMPTY)
        setSerializationInclusion(JsonInclude.Include.NON_NULL)

        val customModule = SimpleModule()
        customModule.addDeserializer(ZonedDateTime::class.java, ZonedDateTimeDeserializer())
        customModule.addSerializer(ZonedDateTime::class.java, ZonedDateTimeSerializer())

        registerModule(JavaTimeModule())
        registerModule(customModule)
    }
}