package com.sidukov.kabar.data.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {

    @Query("SELECT * FROM entitynews")
    fun getAll(): Flow<List<EntityNews>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNews(newsData: List<EntityNews>)

    @Delete
    fun deleteNews(newsData: EntityNews)

}

@Dao
interface NewsBookmarkDao {
    @Query("SELECT * FROM entitynews")
    fun getBookmarkNews(): Flow<List<EntityNews>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addToBookmarkNews(newsData:EntityNews)

    @Update
    fun updateBookmarkNews(newsData: EntityNews)

    @Delete
    fun deleteBookmarkNews(newsData: EntityNews)
}