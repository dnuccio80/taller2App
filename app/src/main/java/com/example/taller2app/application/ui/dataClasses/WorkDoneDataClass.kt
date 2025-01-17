package com.example.taller2app.application.ui.dataClasses

import java.text.NumberFormat
import java.util.Locale

data class WorkDoneDataClass(
    val description:String,
    val quantity:Int,
    val unitPrice:Int,
    val totalPrice:Int = quantity * unitPrice
)

fun WorkDoneDataClass.formatNumber(value: Int): String {
    val formatter = NumberFormat.getInstance(Locale("es", "AR"))
    return formatter.format(value)
}