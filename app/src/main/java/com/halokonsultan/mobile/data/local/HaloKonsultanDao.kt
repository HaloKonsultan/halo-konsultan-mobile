package com.halokonsultan.mobile.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.halokonsultan.mobile.data.model.Category
import com.halokonsultan.mobile.data.model.Consultant
import com.halokonsultan.mobile.data.model.Consultation
import com.halokonsultan.mobile.data.model.Profile
import kotlinx.coroutines.flow.Flow

@Dao
interface HaloKonsultanDao {

    @Query("SELECT * FROM categories ORDER BY RANDOM() LIMIT 5")
    fun getRandomCategories(): Flow<List<Category>>

    @Query("SELECT * FROM consultants WHERE city = :city")
    fun getNearestConsultants(city: String): Flow<List<Consultant>>

    @Query("SELECT * FROM consultants WHERE category_id = :categoryId")
    fun getConsultantByCategory(categoryId: Int): Flow<List<Consultant>>

    @Query("SELECT * FROM consultations WHERE user_id = :id AND status = :status")
    fun getConsultationsByStatus(id:Int, status: String): Flow<List<Consultation>>

    @Query("SELECT * FROM profile WHERE id = :id")
    fun getProfile(id: Int): Flow<Profile>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertCategories(categories: List<Category>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertConsultants(consultants: List<Consultant>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertConsultations(consultations: List<Consultation>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertProfile(profile: Profile)

    @Query("DELETE FROM categories")
    suspend fun deleteAllCategories()

    @Query("DELETE FROM consultants WHERE city = :city")
    suspend fun deleteConsultantsByCity(city: String)

    @Query("DELETE FROM consultants WHERE category_id = :id")
    suspend fun deleteConsultantsByCategory(id: Int)

    @Query("DELETE FROM consultations WHERE status = :status")
    suspend fun deleteConsultationsByStatus(status: String)

    @Query("DELETE FROM profile WHERE id = :id")
    suspend fun deleteProfile(id: Int)
}