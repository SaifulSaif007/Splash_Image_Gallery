package com.saiful.core.di

import com.saiful.core.components.ComponentInitializer
import com.saiful.core.components.logger.TimberInitializer
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
    abstract fun bindTimber(timber: TimberInitializer): ComponentInitializer
}