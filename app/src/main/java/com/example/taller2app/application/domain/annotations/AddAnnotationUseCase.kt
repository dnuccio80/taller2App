package com.example.taller2app.application.domain.annotations

import com.example.taller2app.application.data.annotations.AnnotationsRepository
import com.example.taller2app.application.ui.dataClasses.AnnotationsDataClass
import javax.inject.Inject

class AddAnnotationUseCase @Inject constructor(private val annotationsRepository: AnnotationsRepository) {
    suspend operator fun invoke(annotation: AnnotationsDataClass) = annotationsRepository.addAnnotation(annotation)
}