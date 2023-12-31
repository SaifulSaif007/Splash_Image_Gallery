package com.saiful.data.model.photo

import com.saiful.data.model.User
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Sponsorship(
    @Json(name = "impression_urls")
    val impressionUrls: List<String>,
    @Json(name = "sponsor")
    val sponsor: User,
    @Json(name = "tagline")
    val tagline: String,
    @Json(name = "tagline_url")
    val taglineUrl: String
)