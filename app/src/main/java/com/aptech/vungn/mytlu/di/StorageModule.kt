package com.aptech.vungn.mytlu.di

import com.aptech.vungn.mytlu.util.LoginStateStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StorageModule {
    @Provides
    @Singleton
    fun provideLoginStateStorage(): LoginStateStorage = LoginStateStorage()
}