package com.example.taller2app.application.data.workList

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

    @Query("SELECT * FROM WorkEntity WHERE description LIKE '%' || :query || '%'")
    fun getWorkByDescription(query: String): Flow<List<WorkEntity>>

    @Query("SELECT * FROM WorkEntity WHERE id = :workId")
    suspend fun getWorkById(workId: Int): WorkEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNewWork(workEntity: WorkEntity)

    @Delete
    suspend fun deleteWork(workEntity: WorkEntity)

    @Update
    suspend fun updateWork(workEntity: WorkEntity)


}