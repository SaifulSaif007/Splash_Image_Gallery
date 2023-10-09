package com.saiful.data.di

import com.saiful.data.repository.PhotoRepositoryImpl
import com.saiful.domain.repository.PhotoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
internal interface RepositoryModule {

    @Binds
    fun bindPhotoRepository(photoRepositoryImpl: PhotoRepositoryImpl): PhotoRepository
}