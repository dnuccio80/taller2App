package com.example.taller2app.application.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkDao {

    @Query("SELECT * FROM WorkEntity")
    fun getAllWorkList(): Flow<List<WorkEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addNewWork(workEntity: WorkEntity)

    @Delete
    fun deleteWork(workEntity: WorkEntity)

    @Update
    fun updateWork(workEntity: WorkEntity)


}