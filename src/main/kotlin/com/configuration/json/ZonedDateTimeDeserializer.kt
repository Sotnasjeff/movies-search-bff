package com.configuration.json

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class ZonedDateTimeDeserializer: JsonDeserializer<ZonedDateTime>() {

    override fun deserialize(p: JsonParser, ctxt: DeserializationContext?): ZonedDateTime {
        val value = p.readValueAs(String::class.java)
        return ZonedDateTime.parse(value, PATTERN)
    }

    companion object {
        val PATTERN = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm[:ss[.SSS]][XX][XXX]")!!
    }
}