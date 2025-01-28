package com.example.taller2app.application.domain.worksDone

import com.example.taller2app.application.data.workDone.WorkDoneRepository
import javax.inject.Inject

class DeleteAllWorkDoneDataUseCase @Inject constructor(private val workDoneRepository: WorkDoneRepository)  {
    suspend operator fun invoke() = workDoneRepository.deleteAllWorkDoneData()
}