package com.saiful.splashgallery.logger

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
abstract class LoggerModule {

    @Binds
    @IntoSet
    abstract fun bindTimber(timber: TimberInitializer): Initializer
}