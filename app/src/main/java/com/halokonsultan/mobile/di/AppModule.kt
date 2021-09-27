package com.halokonsultan.mobile.di

import com.halokonsultan.mobile.data.HaloKonsultanRepository
import com.halokonsultan.mobile.data.remote.HaloKonsultanApi
import com.halokonsultan.mobile.data.remote.RetrofitInstance.Companion.api
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApi(): HaloKonsultanApi = api

    @Singleton
    @Provides
    fun provideRepository(api: HaloKonsultanApi): HaloKonsultanRepository =
            HaloKonsultanRepository(api)

}