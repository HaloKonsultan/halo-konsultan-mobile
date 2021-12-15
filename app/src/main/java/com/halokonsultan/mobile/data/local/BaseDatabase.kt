package com.halokonsultan.mobile.data.local

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.halokonsultan.mobile.data.model.*

@Database(
    entities = [
        Category::class,
        Consultant::class,
        Consultation::class,
        Profile::class,
        Chat::class,
        Message::class,
        Review::class
               ],
    version = 2,
    exportSchema = true,
    autoMigrations = [AutoMigration(from = 1, to = 2)]
)
abstract class BaseDatabase : RoomDatabase() {
    abstract fun getDao(): BaseDao

    companion object {
        @Volatile
        private var instance: BaseDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                BaseDatabase::class.java,
                "halo_konsultan_db.db"
            ).build()
    }
}