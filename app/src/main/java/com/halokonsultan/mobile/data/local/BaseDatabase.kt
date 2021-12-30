package com.halokonsultan.mobile.data.local

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.DeleteTable
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.AutoMigrationSpec
import com.halokonsultan.mobile.data.model.*

@Database(
    entities = [
        Category::class,
        Consultant::class,
        Consultation::class,
        Profile::class,
        Chat::class,
        Message::class
               ],
    version = 4,
    exportSchema = true,
    autoMigrations = [
        AutoMigration(
            from = 3,
            to = 4,
            spec = BaseDatabase.BaseAutoMigration::class
        )
    ]
)
abstract class BaseDatabase : RoomDatabase() {
    abstract fun getDao(): BaseDao

    @DeleteTable.Entries(DeleteTable(tableName = "reviews"))
    class BaseAutoMigration: AutoMigrationSpec

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