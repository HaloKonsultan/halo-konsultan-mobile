package com.halokonsultan.mobile.di

import android.content.Context
import com.halokonsultan.mobile.data.BaseRepository
import com.halokonsultan.mobile.data.local.BaseDatabase
import com.halokonsultan.mobile.data.preferences.Preferences
import com.halokonsultan.mobile.data.remote.BaseApi
import com.halokonsultan.mobile.data.remote.LocationApi
import com.halokonsultan.mobile.data.remote.RetrofitInstance
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideApi(): BaseApi = RetrofitInstance().createApi()

    @Singleton
    @Provides
    fun provideLocationApi(): LocationApi = RetrofitInstance().createLocationApi()

    @Singleton
    @Provides
    fun providePreferences(): Preferences = Preferences.instance

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): BaseDatabase
    = BaseDatabase(context)

    @Provides
    fun provideRepository(
            api: BaseApi,
            locationApi: LocationApi,
            preferences: Preferences,
            db: BaseDatabase)
    : BaseRepository = BaseRepository(api, locationApi, preferences, db)

}