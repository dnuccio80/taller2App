package com.example.taller2app.application.ui.dataClasses

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.taller2app.application.data.workDone.WorkDoneEntity
import com.google.gson.Gson
import java.text.NumberFormat
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

data class WorkDoneDataClass(
    val id:Int = 0,
    val workDataClass:WorkDataClass,
    val quantity:Int,
    val dateModified:Long = System.currentTimeMillis()
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
        quantity = quantity,
        dateModified = dateModified
    )
}

@RequiresApi(Build.VERSION_CODES.O)
fun WorkDoneDataClass.getLocalDate(dateModified: Long): String {
    val format = DateTimeFormatter.ofPattern("dd-MM-yyyy")
    val localDateTime = Instant.ofEpochMilli(dateModified)
        .atZone(ZoneId.systemDefault())
        .toLocalDateTime()
    return localDateTime.format(format)
}
