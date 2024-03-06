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
        visibleTabs = visibleTabs(this)
    )


private fun visibleTabs(profile: Profile): List<String> {
    val list = mutableListOf<String>()
    if (profile.totalPhotos > 0) {
        list.add(PHOTOS)
    }
    if (profile.totalLikes > 0) {
        list.add(LIKES)
    }
    if (profile.totalCollections > 0) {
        list.add(COLLECTIONS)
    }
    return list
}

const val PHOTOS = "PHOTOS"
const val LIKES = "LIKES"
const val COLLECTIONS = "COLLECTIONS"