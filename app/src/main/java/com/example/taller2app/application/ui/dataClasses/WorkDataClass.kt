package com.example.taller2app.application.ui.dataClasses

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.taller2app.application.data.workList.WorkEntity
import java.text.NumberFormat
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

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

fun WorkDataClass.formatNumber(value: Int): String {
    val formatter = NumberFormat.getInstance(Locale("es", "AR"))
    return formatter.format(value)
}

@RequiresApi(Build.VERSION_CODES.O)
fun WorkDataClass.getLocalDate(dateModified: Long): String {
    val format = DateTimeFormatter.ofPattern("dd-MM-yyyy")
    val localDateTime = Instant.ofEpochMilli(dateModified)
        .atZone(ZoneId.systemDefault())
        .toLocalDateTime()
    return localDateTime.format(format)
}
