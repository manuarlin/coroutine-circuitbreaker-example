package com.example.coroutinecircuitbreakerexample

import io.github.resilience4j.circuitbreaker.CircuitBreaker.State
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
@OptIn(ExperimentalCoroutinesApi::class)
class DemoWithoutAnnotationTests @Autowired constructor(
    val demoWithoutAnnotationService: DemoWithoutAnnotation
) {

    @BeforeEach
    fun setup() {
        demoWithoutAnnotationService.customCircuitBreaker.transitionToClosedState()
    }


    @Test
    fun `should open circuit breaker on non suspending function`()  {
        demoWithoutAnnotationService.foo(shouldFail = true)

        assertThat(demoWithoutAnnotationService.customCircuitBreaker.state).isEqualTo(State.OPEN)
    }

    @Test
    fun `should open circuit breaker on suspending function`() = runTest {
        demoWithoutAnnotationService.suspendingFoo(shouldFail = true)

        assertThat(demoWithoutAnnotationService.customCircuitBreaker.state).isEqualTo(State.OPEN)
    }

}
