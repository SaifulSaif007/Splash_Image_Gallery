package com.saiful.domain.usecase

import androidx.paging.PagingData
import com.saiful.core.domain.UseCase
import com.saiful.data.repository.profile.ProfileRepository
import com.saiful.domain.mapper.toPhotoItem
import com.saiful.domain.model.PhotoItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProfileLikedPhotosUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) : UseCase<String, Flow<PagingData<PhotoItem>>>() {
    override suspend fun execute(params: String): Flow<PagingData<PhotoItem>> {
        return profileRepository.profileLikedPhotos(params).toPhotoItem()
    }

}