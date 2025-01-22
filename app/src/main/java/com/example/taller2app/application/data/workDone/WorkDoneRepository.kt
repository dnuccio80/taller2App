package com.example.taller2app.application.data.workDone

import com.example.taller2app.application.data.workList.toWorkDataClass
import com.example.taller2app.application.ui.dataClasses.WorkDataClass
import com.example.taller2app.application.ui.dataClasses.WorkDoneDataClass
import com.example.taller2app.application.ui.dataClasses.toWorkDoneDataEntity
import com.example.taller2app.application.ui.dataClasses.toWorkEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WorkDoneRepository @Inject constructor(private val workDoneDao: WorkDoneDao) {

    fun getAllWorkDoneList(): Flow<List<WorkDoneDataClass>> = workDoneDao.getAllWorkDoneList().map {
        it.map { entity ->
            entity.toWorkDoneDataClass()
        }
    }

    suspend fun addNewWorkDone(workDone:WorkDoneDataClass) {
        workDoneDao.deleteWorkDone(workDone.toWorkDoneDataEntity())
    }

    suspend fun updateWorkDone(workDone:WorkDoneDataClass){
        workDoneDao.deleteWorkDone(workDone.toWorkDoneDataEntity())
    }

    suspend fun deleteWorkDone(workDone:WorkDoneDataClass){
        workDoneDao.deleteWorkDone(workDone.toWorkDoneDataEntity())
    }

}