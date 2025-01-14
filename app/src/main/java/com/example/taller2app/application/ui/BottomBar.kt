package com.example.taller2app.application.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.example.taller2app.R
import com.example.taller2app.ui.theme.CardBackground

@Composable
fun BottomBarItem() {

    var index by rememberSaveable { mutableIntStateOf(0) }

    NavigationBar(
        Modifier.fillMaxWidth(),
        containerColor = CardBackground,
        contentColor = Color.White
    ) {
        NavigationBarItem(
            selected = index == 0,
            onClick = { index = 0 },
            icon = { Icon(Icons.Filled.Home, contentDescription = "Home button") },
            label = { Text(text = "Home") }
        )
        NavigationBarItem(
            selected = index == 1,
            onClick = { index = 1 },
            icon = {
                Icon(
                    painter = painterResource(R.drawable.ic_work_done),
                    contentDescription = "Home button"
                )
            },
            label = { Text(text = "Work Done") }
        )
        NavigationBarItem(
            selected = index == 2,
            onClick = { index = 2 },
            icon = {
                Icon(
                    painter = painterResource(R.drawable.ic_annotations),
                    contentDescription = "Home button"
                )
            },
            label = { Text(text = "Annotations") }
        )
    }
}