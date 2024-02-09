package com.saiful.domain.mapper

import com.saiful.data.model.photo.details.PhotoDetails
import com.saiful.data.utils.UNKNOWN
import com.saiful.domain.model.PhotoDetailsItem
import kotlin.math.ln
import kotlin.math.pow

internal fun PhotoDetails.toPhotoDetailsItem() =
    PhotoDetailsItem(
        profileName = user.name,
        profileImage = user.profileImage.small,
        mainImage = urls.regular,
        thumbnailImage = urls.thumb,
        camera = (exif.model ?: UNKNOWN).trim(),
        aperture = if (exif.aperture != null) "f/${exif.aperture}" else UNKNOWN,
        focalLength = if (exif.focalLength != null) "${exif.focalLength}mm" else UNKNOWN,
        shutterSpeed = if (exif.exposureTime != null) "${exif.exposureTime}s" else UNKNOWN,
        iso = if (exif.iso != null) "${exif.iso}" else UNKNOWN,
        views = views.formatNumberWithSuffix(),
        dimensions = "$width x $height",
        downloads = downloads.formatNumberWithSuffix(),
        likes = likes.formatNumberWithSuffix(),
        tags = tags.map { tag ->
            tag.title
        },
        userName = user.username

    )


fun Long.formatNumberWithSuffix(): String {
    if (this < 1_000) {
        return this.toString()
    }

    val exp = (ln(this.toDouble()) / ln(1_000.0)).toInt()
    val formattedValue = String.format("%.1f", this / 1_000.0.pow(exp.toDouble()))

    val suffix = when (exp) {
        1 -> "K"
        2 -> "M"
        3 -> "B"
        else -> ""
    }

    return "$formattedValue$suffix"
}