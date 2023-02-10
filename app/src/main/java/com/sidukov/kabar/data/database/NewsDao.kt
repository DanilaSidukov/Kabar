package com.sidukov.kabar.data.database

import androidx.room.*
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NewsDao {

    @Query("SELECT * FROM entitynews")
    suspend fun getAll(): List<EntityNews>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNews(newsData: EntityNews)

    @Delete
    suspend fun deleteNews(newsData: EntityNews)

}

@Dao
interface NewsBookmarkDao {
    @Query ("SELECT * FROM entitynews")
    suspend fun getBookmarkNews(): List<EntityNews>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToBookmarkNews(newsData: EntityNews)

    @Update
    suspend fun updateBookmarkNews(newsData: EntityNews)

    @Delete
    suspend fun deleteBookmarkNews(newsData: EntityNews)
}