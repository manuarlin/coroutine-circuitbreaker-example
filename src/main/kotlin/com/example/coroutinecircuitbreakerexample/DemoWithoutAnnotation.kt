package com.example.coroutinecircuitbreakerexample

import io.github.resilience4j.circuitbreaker.CircuitBreaker
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig
import io.github.resilience4j.kotlin.circuitbreaker.executeSuspendFunction
import kotlinx.coroutines.delay
import org.springframework.stereotype.Component

@Component
class DemoWithoutAnnotation {

    val customCircuitBreaker: CircuitBreaker = CircuitBreaker.of(
        "myCustomCircuitBreaker",
        CircuitBreakerConfig.custom()
            .slidingWindowSize(2)
            .minimumNumberOfCalls(1)
            .failureRateThreshold(50f)
            .recordResult(RecordResultPredicate())
            .build()
    )

    suspend fun suspendingFoo(shouldFail: Boolean): String {
        return customCircuitBreaker.executeSuspendFunction {
            someSuspendingFunction()
            if (shouldFail) FAILURE_VALUE else SUCCESS_VALUE
        }
    }

    fun foo(shouldFail: Boolean): String {
        return customCircuitBreaker.executeSupplier {
            if (shouldFail) FAILURE_VALUE else SUCCESS_VALUE
        }
    }

    suspend fun someSuspendingFunction() {
        delay(50)
    }
}