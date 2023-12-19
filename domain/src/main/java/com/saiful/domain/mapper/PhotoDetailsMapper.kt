package com.saiful.domain.mapper

import com.saiful.data.model.photo.details.PhotoDetails
import com.saiful.domain.model.PhotoDetailsItem

internal fun PhotoDetails.toPhotoDetailsItem() =
    PhotoDetailsItem(
        profileName = this.user.name,
        profileImage = this.user.profileImage.small,
        mainImage = this.urls.regular,
        thumbnailImage = this.urls.thumb,
        camera = exif.model.trim(),
        aperture = "f/${exif.aperture}",
        focalLength = "${exif.focalLength}mm",
        shutterSpeed = "${exif.exposureTime}s",
        iso = "${exif.iso}",
        views = "$views",
        dimensions = this.width.toString() + " x " + this.height.toString(),
        downloads = this.downloads.toString(),
        likes = this.likes.toString(),
        tags = this.tags.map { tag ->
            tag.title
        }
    )