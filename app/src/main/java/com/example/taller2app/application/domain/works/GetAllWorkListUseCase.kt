package com.example.taller2app.application.domain.works

import com.example.taller2app.application.data.workList.WorkRepository
import javax.inject.Inject

class GetAllWorkListUseCase @Inject constructor(private val workRepository: WorkRepository) {

    operator fun invoke() = workRepository.getAllWorkList()

}