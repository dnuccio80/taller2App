package com.example.taller2app.application.data.workDone

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.taller2app.application.data.workList.WorkDao
import com.example.taller2app.application.data.workList.WorkEntity
import com.example.taller2app.application.data.workList.toWorkDataClass
import com.example.taller2app.application.ui.dataClasses.WorkDataClass
import com.example.taller2app.application.ui.dataClasses.WorkDoneDataClass
import com.google.gson.Gson

@Entity(foreignKeys = [ForeignKey(
    entity = WorkEntity::class,
    parentColumns = ["id"],
    childColumns = ["workId"],
    onUpdate = ForeignKey.CASCADE,
    onDelete = ForeignKey.CASCADE
)])
data class WorkDoneEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val workId:Int = 0,
    val quantity: Int,
    val dateModified: Long
)
