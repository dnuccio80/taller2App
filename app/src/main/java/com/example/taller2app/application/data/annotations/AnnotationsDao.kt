package com.example.taller2app.application.data.annotations

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface AnnotationsDao {

    @Query("SELECT * FROM AnnotationsEntity")
    fun getAllAnnotations(): Flow<List<AnnotationsEntity>>

    @Insert
    suspend fun addAnnotation(annotationsEntity: AnnotationsEntity)

    @Delete
    suspend fun deleteAnnotation(annotationsEntity: AnnotationsEntity)

    @Update
    suspend fun updateAnnotation(annotationsEntity: AnnotationsEntity)


}