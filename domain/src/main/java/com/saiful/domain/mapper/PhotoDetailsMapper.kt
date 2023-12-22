package com.saiful.domain.mapper

import com.saiful.data.model.photo.details.PhotoDetails
import com.saiful.data.utils.UNKNOWN
import com.saiful.domain.model.PhotoDetailsItem

internal fun PhotoDetails.toPhotoDetailsItem() =
    PhotoDetailsItem(
        profileName = this.user.name,
        profileImage = this.user.profileImage.small,
        mainImage = this.urls.regular,
        thumbnailImage = this.urls.thumb,
        camera = (exif.model ?: UNKNOWN).trim(),
        aperture = if (exif.aperture != null) "f/${exif.aperture}" else UNKNOWN,
        focalLength = if (exif.focalLength != null) "${exif.focalLength}mm" else UNKNOWN,
        shutterSpeed = if (exif.exposureTime != null) "${exif.exposureTime}s" else UNKNOWN,
        iso = if (exif.iso != null) "${exif.iso}" else UNKNOWN,
        views = "$views",
        dimensions = this.width.toString() + " x " + this.height.toString(),
        downloads = this.downloads.toString(),
        likes = this.likes.toString(),
        tags = this.tags.map { tag ->
            tag.title
        }
    )