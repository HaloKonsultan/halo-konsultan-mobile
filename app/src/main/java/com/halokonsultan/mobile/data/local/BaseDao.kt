package com.halokonsultan.mobile.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.halokonsultan.mobile.data.model.*
import kotlinx.coroutines.flow.Flow

@Dao
interface BaseDao {

    @Query("SELECT * FROM categories ORDER BY RANDOM() LIMIT 5")
    fun getRandomCategories(): Flow<List<Category>>

    @Query("SELECT * FROM consultants WHERE city = :city")
    fun getNearestConsultants(city: String): Flow<List<Consultant>>

    @Query("SELECT * FROM consultants WHERE category_id = :categoryId")
    fun getConsultantByCategory(categoryId: Int): Flow<List<Consultant>>

    @Query("SELECT * FROM consultations WHERE user_id = :id AND status = :status")
    fun getConsultationsByStatus(id:Int, status: String): Flow<List<Consultation>>

    @Query("SELECT * FROM consultations WHERE user_id = :userId")
    fun getLatestConsultations(userId: Int): Flow<List<Consultation>>

    @Query("SELECT * FROM profile WHERE id = :id")
    fun getProfile(id: Int): Flow<Profile>

    @Query("SELECT * FROM chats WHERE user_id = :userId")
    fun getChatList(userId: Int): Flow<List<Chat>>

    @Query("SELECT * FROM messages WHERE forum_id = :id")
    fun getMessages(id: Int): Flow<List<Message>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertCategories(categories: List<Category>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertConsultants(consultants: List<Consultant>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertConsultations(consultations: List<Consultation>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertLatestConsultations(consultations: List<Consultation>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertProfile(profile: Profile)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertChats(chats: List<Chat>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertMessages(messages: List<Message>)

    @Query("DELETE FROM categories")
    suspend fun deleteAllCategories()

    @Query("DELETE FROM consultants WHERE city = :city")
    suspend fun deleteConsultantsByCity(city: String)

    @Query("DELETE FROM consultants WHERE category_id = :id")
    suspend fun deleteConsultantsByCategory(id: Int)

    @Query("DELETE FROM consultations WHERE status = :status")
    suspend fun deleteConsultationsByStatus(status: String)

    @Query("DELETE FROM consultations WHERE user_id = :id")
    suspend fun deleteLatestConsultation(id: Int)

    @Query("DELETE FROM profile WHERE id = :id")
    suspend fun deleteProfile(id: Int)

    @Query("DELETE FROM chats WHERE user_id = :id")
    suspend fun deleteChats(id: Int)

    @Query("DELETE FROM messages WHERE forum_id = :id")
    suspend fun deleteMessages(id: Int)
}