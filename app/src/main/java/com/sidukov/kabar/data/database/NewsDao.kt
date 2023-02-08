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

    @Delete
    suspend fun deleteNews(newsData: EntityNews)

}

@Dao
interface NewsBookmarkDao {
    @Query ("SELECT * FROM entitybookmarknews")
    suspend fun getBookmarkNews(): List<EntityBookmarkNews>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToBookmarkNews(newsData: EntityBookmarkNews)

    @Delete
    suspend fun deleteBookmarkNews(newsData: EntityBookmarkNews)
}