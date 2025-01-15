package com.example.taller2app.application.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.example.taller2app.R
import com.example.taller2app.application.data.Routes
import com.example.taller2app.ui.theme.CardBackground

@Composable
fun BottomBarItem(navController: NavHostController) {

    var index by rememberSaveable { mutableIntStateOf(0) }

    NavigationBar(
        Modifier.fillMaxWidth(),
        containerColor = CardBackground,
        contentColor = Color.White
    ) {
        NavigationBarItem(
            selected = index == 0,
            onClick = {
                index = 0
                navController.navigate(Routes.Home.route)
            },
            icon = { Icon(Icons.Filled.Home, contentDescription = "Home button") },
            label = { Text(text = stringResource(R.string.home)) }
        )
        NavigationBarItem(
            selected = index == 1,
            onClick = {
                index = 1
                navController.navigate(Routes.Works.route)
            },
            icon = {
                Icon(
                    painter = painterResource(R.drawable.ic_work_done),
                    contentDescription = "Home button"
                )
            },
            label = { Text(text = stringResource(R.string.works)) }
        )
        NavigationBarItem(
            selected = index == 2,
            onClick = {
                index = 2
                navController.navigate(Routes.Annotations.route)
            },
            icon = {
                Icon(
                    painter = painterResource(R.drawable.ic_annotations),
                    contentDescription = "Home button"
                )
            },
            label = { Text(text = stringResource(R.string.annotations)) }
        )
    }
}