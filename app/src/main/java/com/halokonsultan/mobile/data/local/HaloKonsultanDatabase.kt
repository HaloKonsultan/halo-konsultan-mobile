package com.halokonsultan.mobile.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.halokonsultan.mobile.data.model.Category
import com.halokonsultan.mobile.data.model.Consultant
import com.halokonsultan.mobile.data.model.Consultation
import com.halokonsultan.mobile.data.model.Profile

@Database(
    entities = [
        Category::class,
        Consultant::class,
        Consultation::class,
        Profile::class
               ],
    version = 1,
    exportSchema = false
)
abstract class HaloKonsultanDatabase : RoomDatabase() {
    abstract fun getDao(): HaloKonsultanDao

    companion object {
        @Volatile
        private var instance: HaloKonsultanDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                HaloKonsultanDatabase::class.java,
                "halo_konsultan_db.db"
            ).build()
    }
}