package com.example.taller2app.application.ui.dataClasses

import java.time.Instant

data class WorkDataClass(
    val description:String,
    val unitPrice:Int,
    val dateModified: Long = System.currentTimeMillis()
)

