package com.example.taller2app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.taller2app.application.data.Routes
import com.example.taller2app.application.ui.AnnotationsScreen
import com.example.taller2app.application.ui.BottomBarItem
import com.example.taller2app.application.ui.HomeScreen
import com.example.taller2app.application.ui.WorkListScreen
import com.example.taller2app.ui.theme.Taller2AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Taller2AppTheme {

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        BottomBarItem()
                    }
                ) { innerPadding ->

//                    val navController = rememberNavController()
//
//                    NavHost(navController, startDestination = "home") {
//                        composable(Routes.Home.route) { HomeScreen(innerPadding) }
//                    }
//                    WorkListScreen(innerPadding)
                    AnnotationsScreen(innerPadding)
//                        HomeScreen(innerPadding)
                }
            }
        }
    }
}
