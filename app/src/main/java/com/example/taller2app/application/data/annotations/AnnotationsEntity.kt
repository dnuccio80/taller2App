package com.example.taller2app.application.data.annotations

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.taller2app.application.ui.dataClasses.AnnotationsDataClass

@Entity
data class AnnotationsEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
    val title:String,
    val description:String,
)

fun AnnotationsEntity.toAnnotationsDataClass() = AnnotationsDataClass(
    id = id,
    title = title,
    description = description
)
