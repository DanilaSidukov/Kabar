package com.sidukov.kabar.data.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsBookmarkDao {
    @Query("SELECT * FROM entitynews")
    fun getBookmarkNews(): Flow<List<EntityNews>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToBookmarkNews(newsData:EntityNews)

    @Update
    suspend fun updateBookmarkNews(newsData: EntityNews)

    @Delete
    suspend fun deleteBookmarkNews(newsData: EntityNews)
}