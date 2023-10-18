package com.saiful.test.unit

import com.saiful.core.domain.DomainException
import org.junit.After
import org.junit.Before

abstract class BaseTest {

    protected val genericErrorMessage: String = "Something went wrong"

    protected val domainException = DomainException(
        error = "Error",
        message = genericErrorMessage,
        code = 0
    )

    @Before
    abstract fun setup()

    @After
    abstract fun tearDown()

}