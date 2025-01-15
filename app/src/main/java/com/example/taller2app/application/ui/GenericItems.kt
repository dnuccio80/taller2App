package com.example.taller2app.application.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.taller2app.ui.theme.ButtonColor
import com.example.taller2app.ui.theme.TextColor

@Composable
fun SimpleFabItem(modifier: Modifier, innerPadding: PaddingValues) {

    FloatingActionButton(
        onClick = {  },
        modifier = modifier.padding(
            bottom = innerPadding.calculateBottomPadding() + 16.dp,
            end = 8.dp
        ),
        containerColor = ButtonColor,
        contentColor = TextColor

    ) {
        Icon(Icons.Filled.Add, contentDescription = "Add button")
    }
}