package com.example.taller2app.application.ui.dataClasses

import com.example.taller2app.application.data.workDone.WorkDoneEntity
import com.google.gson.Gson
import java.text.NumberFormat
import java.util.Locale

data class WorkDoneDataClass(
    val id:Int = 0,
    val workDataClass:WorkDataClass,
    val quantity:Int,
)

fun WorkDoneDataClass.formatNumber(value: Int): String {
    val formatter = NumberFormat.getInstance(Locale("es", "AR"))
    return formatter.format(value)
}

fun WorkDoneDataClass.getTotalPrice(): Int {
    return quantity * workDataClass.unitPrice
}

fun WorkDoneDataClass.toWorkDoneDataEntity(): WorkDoneEntity{
    return WorkDoneEntity(
        id = id,
        workId = workDataClass.id,
        quantity = quantity
    )
}