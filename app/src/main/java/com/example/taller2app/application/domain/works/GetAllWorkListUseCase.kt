package com.example.taller2app.application.domain.works

import com.example.taller2app.application.data.workList.WorkRepository
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class GetAllWorkListUseCase @Inject constructor(private val workRepository: WorkRepository) {

    operator fun invoke(query: String) =
        if(query.isEmpty()){
            workRepository.getAllWorkList()
        } else {
            workRepository.getWorkByQuery(query)
        }

}