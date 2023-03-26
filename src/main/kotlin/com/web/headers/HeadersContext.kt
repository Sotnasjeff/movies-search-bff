package com.web.headers

data class HeadersContext(private val headers: Map<String, String>) {

    fun getHeaderValues(): List<HeaderContextValue> = headers.map { HeaderContextValue(it.key, it.value) }
}

data class HeaderContextValue(
    val key: String,
    val value: String
)