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

    @Query("SELECT * FROM profile WHERE id = :id")
    fun getProfile(id: Int): Flow<Profile>

    @Query("SELECT * FROM chats WHERE user_id = :userId")
    fun getChatList(userId: Int): Flow<List<Chat>>

    @Query("SELECT * FROM messages WHERE forum_id = :id")
    fun getMessages(id: Int): Flow<List<Message>>

    @Query("SELECT * FROM reviews WHERE userId = :userId")
    fun getReviews(userId: Int): LiveData<List<Review>>

    @Query("SELECT * FROM reviews WHERE consultationId = :consultationId")
    fun getReview(consultationId: Int): LiveData<Review>

    @Query("SELECT EXISTS(SELECT * FROM reviews WHERE userId = :userId AND consultationId = :consultationId)")
    fun isReviewExist(userId: Int, consultationId: Int): LiveData<Boolean>

    @Query("UPDATE reviews SET hasReviewed = 1 WHERE id = :id")
    suspend fun setHasReview(id: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertCategories(categories: List<Category>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertConsultants(consultants: List<Consultant>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertConsultations(consultations: List<Consultation>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertProfile(profile: Profile)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertChats(chats: List<Chat>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertMessages(messages: List<Message>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertReview(review: Review)

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

    @Query("DELETE FROM chats WHERE user_id = :id")
    suspend fun deleteChats(id: Int)

    @Query("DELETE FROM messages WHERE forum_id = :id")
    suspend fun deleteMessages(id: Int)
}