package com.saiful.domain.mapper

import com.saiful.data.model.profile.Profile
import com.saiful.domain.model.ProfileInfo

internal fun Profile.toProfileInfo() =
    ProfileInfo(
        profileName = this.name,
        profileImage = this.profileImage.medium,
        location = this.location.orEmpty(),
        bio = this.bio.orEmpty(),
        photos = this.totalPhotos.toString(),
        likes = this.totalLikes.toString(),
        collection = this.totalCollections.toString(),
    )