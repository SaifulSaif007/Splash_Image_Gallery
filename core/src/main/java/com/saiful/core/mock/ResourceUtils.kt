package com.saiful.core.mock

import okio.buffer
import okio.source
import java.io.IOException
import java.nio.charset.StandardCharsets

internal object ResourceUtils {
    @Throws(exceptionClasses = [IOException::class])
    fun getJsonString(path : String) : String {
        // Load the JSON response
        return try {
            this.javaClass
                .classLoader
                ?.getResourceAsStream(path)
                ?.source()
                ?.buffer()
                ?.readString(StandardCharsets.UTF_8)
                .orEmpty()
        } catch (exception: IOException) {
            throw exception
        }
    }
}