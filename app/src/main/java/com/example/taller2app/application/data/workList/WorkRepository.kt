package com.example.taller2app.application.data.workList

import com.example.taller2app.application.ui.dataClasses.WorkDataClass
import com.example.taller2app.application.ui.dataClasses.toWorkEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WorkRepository @Inject constructor(private val workDao: WorkDao) {

    fun getAllWorkList(): Flow<List<WorkDataClass>> = workDao.getAllWorkList().map {
        it.map { entity ->
            entity.toWorkDataClass()
        }
    }

    fun getWorkByQuery(query:String): Flow<List<WorkDataClass>> = workDao.getWorkByDescription(query).map {
        it.map { entity ->
            entity.toWorkDataClass()
        }
    }

    suspend fun addNewWork(workDataClass: WorkDataClass) {
        workDao.addNewWork(workDataClass.toWorkEntity())
    }

    suspend fun updateWork(workDataClass: WorkDataClass){
        workDao.updateWork(workDataClass.toWorkEntity())
    }

    suspend fun deleteWork(workDataClass: WorkDataClass){
        workDao.deleteWork(workDataClass.toWorkEntity())
    }

}