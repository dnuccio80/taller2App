package com.example.taller2app.application.domain.worksDone

import com.example.taller2app.application.data.workDone.WorkDoneRepository
import com.example.taller2app.application.ui.dataClasses.WorkDoneDataClass
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllWorkDoneListUseCase @Inject constructor(private val workDoneRepository: WorkDoneRepository) {
    operator fun invoke(): Flow<List<WorkDoneDataClass>> = workDoneRepository.getAllWorkDoneList()
}