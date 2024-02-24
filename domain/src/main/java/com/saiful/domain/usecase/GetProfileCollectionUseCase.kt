package com.saiful.domain.usecase

import androidx.paging.PagingData
import com.saiful.core.domain.UseCase
import com.saiful.data.repository.profile.ProfileRepository
import com.saiful.domain.mapper.toCollectionItem
import com.saiful.domain.model.CollectionItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProfileCollectionUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) : UseCase<String, Flow<PagingData<CollectionItem>>>() {
    override suspend fun execute(params: String): Flow<PagingData<CollectionItem>> {
        return profileRepository.profilePhotoCollections(params).toCollectionItem()
    }
}