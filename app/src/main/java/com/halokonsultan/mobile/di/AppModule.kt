package com.halokonsultan.mobile.di

import com.halokonsultan.mobile.data.HaloKonsultanRepository
import com.halokonsultan.mobile.data.preferences.Preferences
import com.halokonsultan.mobile.data.remote.HaloKonsultanApi
import com.halokonsultan.mobile.data.remote.RetrofitInstance
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideApi(): HaloKonsultanApi = RetrofitInstance().createApi()

    @Singleton
    @Provides
    fun providePreferences(): Preferences = Preferences.instance

    @Provides
    fun provideRepository(api: HaloKonsultanApi, preferences: Preferences): HaloKonsultanRepository =
            HaloKonsultanRepository(api, preferences)

}