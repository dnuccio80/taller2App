package com.example.taller2app.application.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.taller2app.R
import com.example.taller2app.ui.theme.AppBackground
import com.example.taller2app.ui.theme.ButtonColor
import com.example.taller2app.ui.theme.CardBackground

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WorkListScreen(innerPadding: PaddingValues, viewModel: TallerViewModel) {

    val searchWorkText = viewModel.searchWorkText.collectAsState()
    val workList = viewModel.workList.collectAsState()
    val showAddNewWorkDialog = viewModel.showAddNewWorkDialog.collectAsState()

    val addNewWorkDescription = viewModel.addNewWorkDescription.collectAsState()
    val unitPriceNewWorkText = viewModel.unitPriceNewWorkText.collectAsState()

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

            SearchWorkTextField(searchWorkText.value) { viewModel.updateSearchWorkText(it) }
            Spacer(Modifier.size(16.dp))
            LazyColumn(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                if (workList.value.isEmpty()) {
                    item {
                        Text(stringResource(R.string.no_works_found))
                    }
                } else {
                    items(workList.value) {
                        WorkCardItem(
                            description = it.description,
                            unitPrice = it.unitPrice,
                            dateModified = viewModel.getLocalDate(it.dateModified)
                        )
                    }
                }
            }
        }
        SimpleFabItem(
            Modifier.align(Alignment.BottomEnd),
            innerPadding
        ) { viewModel.updateShowAddNewWorkDialog(true) }
        AddNewWorkDialog(
            showAddNewWorkDialog.value,
            addNewWorkDescription.value,
            unitPriceNewWorkText.value,
            onWorkDescriptionChange = { viewModel.updateAddNewWorkDescription(it) },
            onUnitPriceChange = { viewModel.updateUnitPriceNewWorkText(it) },
            onDismiss = { viewModel.updateShowAddNewWorkDialog(false) },
            onAcceptButtonClicked = {
                if(viewModel.hasAllCorrectFields(addNewWorkDescription.value, unitPriceNewWorkText.value)) {
                    viewModel.addNewWork(addNewWorkDescription.value, unitPriceNewWorkText.value.toInt())
                    viewModel.clearAddWorkListDialogData()
                    viewModel.updateShowAddNewWorkDialog(false)
                }
            },
            onDeclineButtonClicked = {
                viewModel.updateShowAddNewWorkDialog(false)
                viewModel.clearAddWorkListDialogData()
            },
        )
    }
}

@Composable
private fun SearchWorkTextField(textValue: String, onValueChange: (String) -> Unit) {
    TextField(
        value = textValue,
        onValueChange = { onValueChange(it) },
        placeholder = { Text(text = stringResource(R.string.search_work)) },
        modifier = Modifier.fillMaxWidth(),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = AppBackground,
            unfocusedContainerColor = AppBackground,
        ),
        singleLine = true,
        trailingIcon = {
            Icon(
                Icons.Filled.Search,
                contentDescription = "Search work button",
                tint = Color.White
            )
        },
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Sentences,
            keyboardType = KeyboardType.Text
        )
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WorkCardItem(description: String, unitPrice: Int, dateModified: String) {
    Card(
        Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = CardBackground
        ),
        border = BorderStroke(
            1.dp,
            brush = Brush.verticalGradient(listOf(AppBackground, ButtonColor))
        )
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(description, modifier = Modifier.weight(1f))
            Text("$ $unitPrice", modifier = Modifier.padding(16.dp))
            Text(dateModified, modifier = Modifier.padding(16.dp))
            Icon(Icons.Filled.Edit, contentDescription = "Edit work", tint = Color.White)
        }
    }

}



