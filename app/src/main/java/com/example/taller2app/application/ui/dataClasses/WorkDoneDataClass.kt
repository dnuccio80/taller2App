package com.example.taller2app.application.ui.dataClasses

import java.text.NumberFormat
import java.util.Locale

data class WorkDoneDataClass(
    val workDataClass:WorkDataClass,
    val quantity:Int,
    val totalPrice:Int = quantity * workDataClass.unitPrice
)

fun WorkDoneDataClass.formatNumber(value: Int): String {
    val formatter = NumberFormat.getInstance(Locale("es", "AR"))
    return formatter.format(value)
}