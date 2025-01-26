package com.example.taller2app.application.ui.dataClasses

import com.example.taller2app.application.data.annotations.AnnotationsEntity

data class AnnotationsDataClass(
    val id:Int = 0,
    val title:String,
    val description:String,
)

fun AnnotationsDataClass.toAnnotationsEntity() = AnnotationsEntity(
    id = id,
    title = title,
    description = description
)