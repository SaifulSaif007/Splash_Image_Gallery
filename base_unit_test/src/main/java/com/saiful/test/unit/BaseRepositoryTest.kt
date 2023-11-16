package com.saiful.test.unit

import com.saiful.core.utils.ErrorMapper

abstract class BaseRepositoryTest : BaseTest() {
    val errorMapper = ErrorMapper()
}