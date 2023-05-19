package com.example.coroutinecircuitbreakerexample

import io.github.resilience4j.circuitbreaker.CircuitBreaker.State
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
@OptIn(ExperimentalCoroutinesApi::class)
class DemoWithAnnotationTests @Autowired constructor(
    val demoWithAnnotationService: DemoWithAnnotation,
    val circuitBreakerRegistry: CircuitBreakerRegistry
) {

    @BeforeEach
    fun setup() {
        circuitBreakerRegistry.circuitBreaker("myCircuitBreaker").transitionToClosedState()
    }


    @Test
    fun `should open circuit breaker on non suspending function`() {
        demoWithAnnotationService.foo(shouldFail = true)

        assertThat(circuitBreakerRegistry.circuitBreaker("myCircuitBreaker").state).isEqualTo(State.OPEN)
    }

    @Test
    fun `should open circuit breaker on suspending function`() = runTest {
        demoWithAnnotationService.suspendingFoo(shouldFail = true)

        assertThat(circuitBreakerRegistry.circuitBreaker("myCircuitBreaker").state).isEqualTo(State.OPEN)
    }

}
