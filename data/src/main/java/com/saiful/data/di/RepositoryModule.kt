package com.saiful.data.di

import com.saiful.data.repository.collection.CollectionRepository
import com.saiful.data.repository.collection.CollectionRepositoryImpl
import com.saiful.data.repository.photo.PhotoRepository
import com.saiful.data.repository.photo.PhotoRepositoryImpl
import com.saiful.data.repository.profile.ProfileRepository
import com.saiful.data.repository.profile.ProfileRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
internal interface RepositoryModule {

    @Binds
    fun bindPhotoRepository(photoRepositoryImpl: PhotoRepositoryImpl): PhotoRepository

    @Binds
    fun bindCollectionRepository(collectionRepositoryImpl: CollectionRepositoryImpl): CollectionRepository

    @Binds
    fun bindProfileRepository(profileRepositoryImpl: ProfileRepositoryImpl): ProfileRepository

}