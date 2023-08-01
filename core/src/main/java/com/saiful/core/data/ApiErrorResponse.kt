package com.saiful.core.data

import com.squareup.moshi.Json

data class ApiErrorResponse(
    @Json(name = "errors") val errors: List<String>
)
