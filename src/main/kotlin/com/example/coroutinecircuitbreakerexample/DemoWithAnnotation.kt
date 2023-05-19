package com.example.coroutinecircuitbreakerexample

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import kotlinx.coroutines.delay
import org.springframework.stereotype.Component

@Component
class DemoWithAnnotation {

    @CircuitBreaker(name = "myCircuitBreaker")
    suspend fun suspendingFoo(shouldFail: Boolean): String {
        someSuspendingFunction()
        return if (shouldFail) FAILURE_VALUE else SUCCESS_VALUE
    }

    @CircuitBreaker(name = "myCircuitBreaker")
    fun foo(shouldFail: Boolean): String {
        return if (shouldFail) FAILURE_VALUE else SUCCESS_VALUE
    }

    suspend fun someSuspendingFunction() {
        delay(50)
    }
}