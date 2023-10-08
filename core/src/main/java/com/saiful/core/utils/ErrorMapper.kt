package com.saiful.core.utils

import com.saiful.core.domain.DomainException
import javax.inject.Inject

class ErrorMapper @Inject constructor() {

    fun toDomainException(exception: Exception): DomainException {
        return DomainException(
            error = exception.localizedMessage ?: toString(),
            message = "Something went wrong",
            code = 0
        )
    }
}