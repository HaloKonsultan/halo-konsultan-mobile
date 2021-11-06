package com.halokonsultan.mobile.di

import android.content.Context
import com.halokonsultan.mobile.data.HaloKonsultanRepository
import com.halokonsultan.mobile.data.local.HaloKonsultanDatabase
import com.halokonsultan.mobile.data.preferences.Preferences
import com.halokonsultan.mobile.data.remote.HaloKonsultanApi
import com.halokonsultan.mobile.data.remote.LocationApi
import com.halokonsultan.mobile.data.remote.RetrofitInstance
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideApi(): HaloKonsultanApi = RetrofitInstance().createApi()

    @Singleton
    @Provides
    fun provideLocationApi(): LocationApi = RetrofitInstance().createLocationApi()

    @Singleton
    @Provides
    fun providePreferences(): Preferences = Preferences.instance

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): HaloKonsultanDatabase
    = HaloKonsultanDatabase(context)

    @Provides
    fun provideRepository(
        api: HaloKonsultanApi,
        locationApi: LocationApi,
        preferences: Preferences,
        db: HaloKonsultanDatabase)
    : HaloKonsultanRepository = HaloKonsultanRepository(api, locationApi, preferences, db)

}