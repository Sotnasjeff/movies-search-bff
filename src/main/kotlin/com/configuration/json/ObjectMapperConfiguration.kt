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
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
    objectMapper.configure(SerializationFeature.INDENT_OUTPUT, false)
    objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true)

    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY)
    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL)

    val customModule = SimpleModule()
    customModule.addDeserializer(ZonedDateTime::class.java, ZonedDateTimeDeserializer())
    customModule.addSerializer(ZonedDateTime::class.java, ZonedDateTimeSerializer())

    objectMapper.registerModule(JavaTimeModule())
    objectMapper.registerModule(customModule)

    return objectMapper
}