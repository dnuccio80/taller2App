package com.example.taller2app.application.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.taller2app.application.data.paymentMethod.PaymentDao
import com.example.taller2app.application.data.paymentMethod.PaymentEntity
import com.example.taller2app.application.data.workDone.WorkDoneDao
import com.example.taller2app.application.data.workDone.WorkDoneEntity
import com.example.taller2app.application.data.workList.WorkDao
import com.example.taller2app.application.data.workList.WorkEntity

@Database(entities = [WorkEntity::class, WorkDoneEntity::class, PaymentEntity::class], version = 3)
abstract class AppDataBase :RoomDatabase(){
    abstract val workDao: WorkDao
    abstract val workDoneDao:WorkDoneDao
    abstract val paymentDao: PaymentDao
}