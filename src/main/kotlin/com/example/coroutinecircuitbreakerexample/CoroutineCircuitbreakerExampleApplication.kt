package com.example.coroutinecircuitbreakerexample

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

const val SUCCESS_VALUE = "foo"
const val FAILURE_VALUE = "bar"

@SpringBootApplication
class CoroutineCircuitbreakerExampleApplication

fun main(args: Array<String>) {
    runApplication<CoroutineCircuitbreakerExampleApplication>(*args)
}
