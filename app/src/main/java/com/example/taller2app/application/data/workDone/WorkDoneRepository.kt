package com.example.taller2app.application.data.workDone

import com.example.taller2app.application.data.workList.WorkDao
import com.example.taller2app.application.data.workList.toWorkDataClass
import com.example.taller2app.application.ui.dataClasses.WorkDataClass
import com.example.taller2app.application.ui.dataClasses.WorkDoneDataClass
import com.example.taller2app.application.ui.dataClasses.toWorkDoneDataEntity
import com.example.taller2app.application.ui.dataClasses.toWorkEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WorkDoneRepository @Inject constructor(private val workDoneDao: WorkDoneDao) {

    fun getAllWorkDoneList(): Flow<List<WorkDoneDataClass>> = workDoneDao.getAllWorkDoneWithWorkList().map {list ->
        list.map {workDoneWithWork ->
            WorkDoneDataClass(
                id = workDoneWithWork.workDoneEntity.id,
                workDataClass = workDoneWithWork.workEntity.toWorkDataClass(),
                quantity = workDoneWithWork.workDoneEntity.quantity
            )

        }
    }

    suspend fun deleteAllWorkDoneData(){
        workDoneDao.deleteAllWorkDoneData()
    }

    suspend fun addNewWorkDone(workDone:WorkDoneDataClass) {
        workDoneDao.addNewWorkDone(workDone.toWorkDoneDataEntity())
    }

    suspend fun updateWorkDone(workDone:WorkDoneDataClass){
        workDoneDao.updateWorkDone(workDone.toWorkDoneDataEntity())
    }

    suspend fun deleteWorkDone(workDone:WorkDoneDataClass){
        workDoneDao.deleteWorkDone(workDone.toWorkDoneDataEntity())
    }

}