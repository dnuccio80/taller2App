package com.example.taller2app.application.domain.annotations

import com.example.taller2app.application.data.annotations.AnnotationsRepository
import javax.inject.Inject

class GetAllAnnotationsUseCase @Inject constructor(private val annotationsRepository: AnnotationsRepository) {
    operator fun invoke() = annotationsRepository.getAllAnnotations()
}