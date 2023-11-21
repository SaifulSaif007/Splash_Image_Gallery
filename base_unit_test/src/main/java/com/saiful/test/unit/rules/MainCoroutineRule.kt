package com.saiful.test.unit.rules

import com.saiful.core.utils.DispatcherProvider
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import org.junit.rules.TestWatcher
import org.junit.runner.Description

/**
 * The default testDispatcher here is UnconfinedTestDispatcher,
 * they are started eagerly on the current thread. This means
 * that they’ll start running immediately, without waiting for their coroutine builder to return.
 * In many cases, this dispatching behavior results in simpler test code,
 * as you don’t need to manually yield the test thread to let new coroutines run.
 * so, pass this param in runTest to start coroutine in test
 *
 * if in runTest no dispatcher defined, TestScope will create a StandardTestDispatcher by default
 * When you start new coroutines on a StandardTestDispatcher, they are queued up on the underlying
 * scheduler, to be run whenever the test thread is free to use.
 * To let these new coroutines run, you need to yield the test thread (free it up for other coroutines to use).
 * This queueing behavior gives you precise control over how new coroutines run during the test,
 * and it resembles the scheduling of coroutines in production code.
 */

@ExperimentalCoroutinesApi
class MainCoroutineRule(val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()) :
    TestWatcher() {

    val testDispatcherProvider = object : DispatcherProvider {
        override fun default(): CoroutineDispatcher = testDispatcher
        override fun io(): CoroutineDispatcher = testDispatcher
        override fun main(): CoroutineDispatcher = testDispatcher
        override fun unconfined(): CoroutineDispatcher = testDispatcher
    }

    override fun starting(description: Description) {
        super.starting(description)
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description) {
        super.finished(description)
        Dispatchers.resetMain()
    }
}