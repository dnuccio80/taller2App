package com.example.taller2app.application.data.workList

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.taller2app.application.ui.dataClasses.WorkDataClass

@Entity
data class WorkEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val description: String,
    val unitPrice: Int,
    val dateModified: Long = System.currentTimeMillis()
)

fun WorkEntity.toWorkDataClass() = WorkDataClass(
    id = id,
    description = description,
    unitPrice = unitPrice,
    dateModified = dateModified
)
