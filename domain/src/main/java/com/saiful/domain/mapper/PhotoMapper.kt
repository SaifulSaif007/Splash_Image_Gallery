package com.saiful.domain.mapper

import com.saiful.data.model.home.Photo
import com.saiful.domain.model.HomeItem

internal fun List<Photo>.toHomeItem(): List<HomeItem> =
    this.map {
        HomeItem(
            profileImage = it.user.profileImage.small,
            profileName = it.user.name,
            sponsored = it.sponsorship != null,
            mainImage = it.urls.small
        )
    }
