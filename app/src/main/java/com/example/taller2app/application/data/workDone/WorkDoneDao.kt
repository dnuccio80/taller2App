package com.example.taller2app.application.data.workDone

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.taller2app.application.data.workList.WorkDao
import com.example.taller2app.application.data.workList.WorkEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkDoneDao {
    @Query("SELECT * FROM WorkDoneEntity")
    fun getAllWorkDoneList(): Flow<List<WorkDoneEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addNewWorkDone(workDone: WorkDoneEntity)

    @Delete
    fun deleteWorkDone(workDone: WorkDoneEntity)

    @Update
    fun updateWorkDone(workDone: WorkDoneEntity)

}