package com.configuration.json

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class ZonedDateTimeSerializer : JsonSerializer<ZonedDateTime>() {

    override fun serialize(value: ZonedDateTime?, gen: JsonGenerator, serializers: SerializerProvider?) {
        if(value == null) {
            gen.writeNull()
        } else {
            gen.writeString(value.format(PATTERN))
        }
    }

    companion object {
        val PATTERN = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX")!!
    }
}