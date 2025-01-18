package com.example.taller2app.application.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [WorkEntity::class], version = 1)
abstract class AppDataBase :RoomDatabase(){
    abstract val workDao: WorkDao
}