package com.example.taller2app.application.data

import androidx.room.Embedded
import androidx.room.Relation
import com.example.taller2app.application.data.workDone.WorkDoneEntity
import com.example.taller2app.application.data.workList.WorkEntity
import com.example.taller2app.application.ui.dataClasses.WorkDataClass

data class WorkWithWorkDoneRelation(
    @Embedded val workDoneEntity: WorkDoneEntity,
    @Relation(
        parentColumn = "workId",
        entityColumn = "id"
    )
    val workEntity: WorkEntity
)
