package com.configuration.circuitbreaker

import io.github.resilience4j.circuitbreaker.CircuitBreaker
import io.github.resilience4j.kotlin.circuitbreaker.executeSuspendFunction

suspend fun <T> CircuitBreaker.attemptAndRecover(tryBlock: suspend () -> T, recoverBlock: suspend (e: Throwable) -> T): T {
    return runCatching{ this.executeSuspendFunction(tryBlock) }.getOrElse { recoverBlock(it) }
}