package com.saiful.data.model.home

internal data class Sponsorship(
    val impressionUrls: List<String>,
    val sponsor: Sponsor,
    val tagline: String,
    val taglineUrl: String
)