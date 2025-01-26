package com.example.taller2app.application.data.annotations

import com.example.taller2app.application.ui.dataClasses.AnnotationsDataClass
import com.example.taller2app.application.ui.dataClasses.toAnnotationsEntity
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AnnotationsRepository @Inject constructor(private val annotationsDao: AnnotationsDao){

    fun getAllAnnotations() = annotationsDao.getAllAnnotations().map {list ->
        list.map {entity ->
            entity.toAnnotationsDataClass()
        }
    }

    suspend fun addAnnotation(annotation: AnnotationsDataClass) = annotationsDao.addAnnotation(annotation.toAnnotationsEntity())

    suspend fun deleteAnnotation(annotation:AnnotationsDataClass) = annotationsDao.deleteAnnotation(annotation.toAnnotationsEntity())

    suspend fun updateAnnotation(annotation: AnnotationsDataClass) = annotationsDao.updateAnnotation(annotation.toAnnotationsEntity())

}