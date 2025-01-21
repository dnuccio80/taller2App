package com.example.taller2app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.taller2app.application.data.Routes
import com.example.taller2app.application.ui.AnnotationsScreen
import com.example.taller2app.application.ui.BottomBarItem
import com.example.taller2app.application.ui.HomeScreen
import com.example.taller2app.application.ui.TallerViewModel
import com.example.taller2app.application.ui.WorkListScreen
import com.example.taller2app.ui.theme.Taller2AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel:TallerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Taller2AppTheme {
                val navController = rememberNavController()

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        BottomBarItem(navController, viewModel)
                    }
                ) { innerPadding ->
                    NavHost(navController, startDestination = Routes.Home.route) {
                        composable(Routes.Home.route) { HomeScreen(innerPadding, viewModel) }
                        composable(Routes.Works.route) { WorkListScreen(innerPadding, viewModel) }
                        composable(Routes.Annotations.route) { AnnotationsScreen(innerPadding) }
                    }


//                    WorkListScreen(innerPadding,viewModel)
//                    AnnotationsScreen(innerPadding)
//                    HomeScreen(innerPadding,viewModel)
                }
            }
        }
    }
}
