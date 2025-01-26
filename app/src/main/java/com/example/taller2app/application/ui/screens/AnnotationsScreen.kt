package com.example.taller2app.application.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taller2app.R
import com.example.taller2app.application.ui.TallerViewModel
import com.example.taller2app.application.ui.dataClasses.AnnotationsDataClass
import com.example.taller2app.application.ui.items.EditAnnotationDialog
import com.example.taller2app.application.ui.items.HorizontalDividerCard
import com.example.taller2app.application.ui.items.NewAnnotationsDialog
import com.example.taller2app.application.ui.items.SimpleFabItem
import com.example.taller2app.ui.theme.AppBackground
import com.example.taller2app.ui.theme.ButtonColor
import com.example.taller2app.ui.theme.CardBackground
import com.example.taller2app.ui.theme.TextColor

@Composable
fun AnnotationsScreen(innerPadding: PaddingValues, viewModel: TallerViewModel) {

    val annotationsList = viewModel.annotationsList.collectAsState()
    var showDialog by rememberSaveable { mutableStateOf(false) }
    var titleText by rememberSaveable { mutableStateOf("") }
    var descriptionText by rememberSaveable { mutableStateOf("") }

    Box(
        Modifier
            .fillMaxSize()
            .background(AppBackground)
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    bottom = innerPadding.calculateBottomPadding(),
                    start = 16.dp,
                    end = 16.dp
                )
        ) {
            Text(stringResource(R.string.annotations), style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.size(16.dp))
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                if (annotationsList.value.isEmpty()) {
                    item {
                        Text(stringResource(R.string.no_annotations_found))
                    }
                } else {
                    items(annotationsList.value) { annotation ->
                        AnnotationCardItem(
                            annotation,
                            onDeleteButtonClicked = { viewModel.deleteAnnotation(annotation) },
                            onEditedAnnotation = { viewModel.updateAnnotation(it) })
                    }
                }

            }
        }
        SimpleFabItem(Modifier.align(Alignment.BottomEnd), innerPadding) { showDialog = true }
        NewAnnotationsDialog(
            showDialog,
            titleText,
            descriptionText,
            onDismiss = {
                showDialog = false
                titleText = ""
                descriptionText = ""
            },
            onTitleChange = { titleText = it },
            onAcceptButtonClicked = {
                if (viewModel.hasAllCorrectFields(titleText, descriptionText)) {
                    viewModel.addAnnotation(
                        AnnotationsDataClass(
                            title = titleText,
                            description = descriptionText
                        )
                    )
                    showDialog = false
                    titleText = ""
                    descriptionText = ""
                }
            },
            onDescriptionChange = { descriptionText = it })
    }
}

@Composable
fun AnnotationCardItem(
    annotation: AnnotationsDataClass,
    onDeleteButtonClicked: () -> Unit,
    onEditedAnnotation: (AnnotationsDataClass) -> Unit
) {

    var showDialog by rememberSaveable { mutableStateOf(false) }
    var titleText by rememberSaveable { mutableStateOf(annotation.title) }
    var descriptionText by rememberSaveable { mutableStateOf(annotation.description) }

    Card(
        Modifier
            .fillMaxWidth()
            .clickable {
                showDialog = true
            },
        colors = CardDefaults.cardColors(
            containerColor = CardBackground
        ),
        border = BorderStroke(
            1.dp,
            brush = Brush.verticalGradient(listOf(AppBackground, ButtonColor))
        )
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(150.dp)
        ) {
            Row(Modifier.fillMaxWidth()) {
                Text(
                    annotation.title,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 16.sp,
                    color = TextColor,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    Icons.Filled.Close, contentDescription = "delete button", tint = TextColor,
                    modifier = Modifier
                        .size(18.dp)
                        .clickable {
                            onDeleteButtonClicked()
                        }
                )
            }

            HorizontalDividerCard()
            Spacer(Modifier.size(9.dp))
            Text(annotation.description)
        }
    }

    EditAnnotationDialog(
        showDialog,
        titleText,
        descriptionText,
        onDismiss = {
            showDialog = false
            titleText = annotation.title
            descriptionText = annotation.description
        },
        onTitleChange = { titleText = it },
        onAcceptButtonClicked = {
            if(titleText.isNotEmpty()){
                onEditedAnnotation(annotation.copy(title = titleText, description = descriptionText))
                showDialog = false
            }
        },
        onDescriptionChange = { descriptionText = it })

}