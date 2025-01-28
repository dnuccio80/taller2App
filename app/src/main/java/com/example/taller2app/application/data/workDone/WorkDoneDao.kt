package com.example.taller2app.application.data.workDone

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.taller2app.application.data.WorkWithWorkDoneRelation
import com.example.taller2app.application.data.workList.WorkDao
import com.example.taller2app.application.data.workList.WorkEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkDoneDao {
//    @Query("SELECT * FROM WorkDoneEntity")
//    fun getAllWorkDoneList(): Flow<List<WorkDoneEntity>>

    @Transaction
    @Query("SELECT * FROM WorkDoneEntity")
    fun getAllWorkDoneWithWorkList(): Flow<List<WorkWithWorkDoneRelation>>

    @Query("DELETE FROM WorkDoneEntity")
    suspend fun deleteAllWorkDoneData()

    @Insert
    suspend fun addNewWorkDone(workDone: WorkDoneEntity)

    @Delete
    suspend fun deleteWorkDone(workDone: WorkDoneEntity)

    @Update
    suspend fun updateWorkDone(workDone: WorkDoneEntity)

}