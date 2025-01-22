package com.example.taller2app.application.data.workDone

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.taller2app.application.ui.dataClasses.WorkDataClass
import com.example.taller2app.application.ui.dataClasses.WorkDoneDataClass
import com.google.gson.Gson

@Entity
data class WorkDoneEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val workDataClass: String,
    val quantity: Int,
    val totalPrice: Int
)

fun WorkDoneEntity.toWorkDoneDataClass(): WorkDoneDataClass {

    val gson = Gson()

    return WorkDoneDataClass(
        id = id,
        workDataClass = gson.fromJson(workDataClass, WorkDataClass::class.java),
        quantity = quantity,
        totalPrice = totalPrice
    )
}
