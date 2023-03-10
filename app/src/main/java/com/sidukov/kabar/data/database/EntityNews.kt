package com.sidukov.kabar.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class EntityNews(
    @PrimaryKey val title: String,
    @ColumnInfo val category: String,
    @ColumnInfo val description: String,
    @ColumnInfo val newsImage: String?,
    @ColumnInfo val author: String,
    @ColumnInfo val date: String
    )