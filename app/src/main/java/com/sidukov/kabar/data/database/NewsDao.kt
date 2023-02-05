package com.sidukov.kabar.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query

@Dao
public interface NewsDao {

    @Query("SELECT * FROM entitynews")
    suspend fun getAll(): List<EntityNews>

    @Delete
    suspend fun deleteNews(newsData: EntityNews)

}