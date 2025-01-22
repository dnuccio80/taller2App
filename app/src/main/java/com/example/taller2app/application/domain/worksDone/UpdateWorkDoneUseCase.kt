package com.example.taller2app.application.domain.worksDone

import com.example.taller2app.application.data.workDone.WorkDoneRepository
import com.example.taller2app.application.ui.dataClasses.WorkDoneDataClass
import javax.inject.Inject

class UpdateWorkDoneUseCase @Inject constructor(private val workDoneRepository: WorkDoneRepository) {
    suspend operator fun invoke(workDone: WorkDoneDataClass) = workDoneRepository.updateWorkDone(workDone)

}