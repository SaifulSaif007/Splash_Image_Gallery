package com.saiful.test.unit.rules

import com.saiful.core.utils.DispatcherProvider
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import org.junit.rules.TestWatcher
import org.junit.runner.Description

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