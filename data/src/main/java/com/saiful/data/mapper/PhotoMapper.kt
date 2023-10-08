package com.saiful.data.mapper

import com.saiful.data.model.home.PhotosResponse
import com.saiful.domain.model.HomeItem

internal fun PhotosResponse.toHomeItem(): List<HomeItem> =
    this.map {
        HomeItem(
            profileImage = it.user.profileImage.small,
            profileName = it.user.name,
            sponsored = it.sponsorship != null,
            mainImage = it.urls.small
        )
    }
