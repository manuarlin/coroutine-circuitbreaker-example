package com.example.coroutinecircuitbreakerexample

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.function.Predicate

class RecordResultPredicate : Predicate<Any> {

    private val logger: Logger = LoggerFactory.getLogger(RecordResultPredicate::class.java)

    override fun test(result: Any): Boolean {
        logger.info("Calling result predicate with result of type ${result.javaClass}")
        return result is String && result == FAILURE_VALUE
    }
}