package com.example.taller2app.application.ui.dataClasses

import com.example.taller2app.application.data.WorkEntity
import java.time.Instant

data class WorkDataClass(
    val id: Int = 0,
    val description: String,
    val unitPrice: Int,
    val dateModified: Long = System.currentTimeMillis()
)

fun WorkDataClass.toWorkEntity() = WorkEntity(
    id = id,
    description = description,
    unitPrice = unitPrice,
    dateModified = dateModified
)
