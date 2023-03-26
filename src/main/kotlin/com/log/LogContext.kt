package com.log

class LogContext {
    private val fields: MutableMap<String, Any?> = mutableMapOf()
    private val context: MutableMap<String, Any?> = mutableMapOf()

    fun setAccountId(accountId: String?) {
        fields[ACCOUNT_ID] = accountId
    }

    fun setRequestId(requestId: String?) {
        fields[REQUEST_ID] = requestId
    }

    fun setSessionId(sessionId: String?) {
        fields[SESSION_ID] = sessionId
    }

    fun putOnContext(key: String, value: Any?) {
        context[key] = value
    }

    fun toMap(): Map<String, Any?> {
        fields[CONTEXT] = context
        return fields.toMap()
    }
}