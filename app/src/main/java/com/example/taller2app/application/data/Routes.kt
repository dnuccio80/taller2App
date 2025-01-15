package com.example.taller2app.application.data

sealed class Routes(val route:String) {
    data object Home: Routes("home")
    data object Works: Routes("works")
    data object Annotations: Routes("annotations")
}