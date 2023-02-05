package com.sidukov.kabar.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [EntityNews::class], version = 1)
abstract class DataBaseNews : RoomDatabase() {

    abstract fun daoNews(): NewsDao

}