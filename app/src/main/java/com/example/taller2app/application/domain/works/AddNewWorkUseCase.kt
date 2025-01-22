package com.example.taller2app.application.domain.works

import com.example.taller2app.application.data.workList.WorkRepository
import com.example.taller2app.application.ui.dataClasses.WorkDataClass
import javax.inject.Inject

class AddNewWorkUseCase @Inject constructor(private val workRepository: WorkRepository) {
    suspend operator fun invoke(description:String,unitPrice:Int) = workRepository.addNewWork(WorkDataClass(description = description, unitPrice = unitPrice))
}