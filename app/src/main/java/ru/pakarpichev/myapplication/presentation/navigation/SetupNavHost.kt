package ru.pakarpichev.myapplication.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.pakarpichev.myapplication.presentation.screens.MainScreen
import ru.pakarpichev.myapplication.presentation.screens.SplashScreen
import ru.pakarpichev.myapplication.presentation.screens.webViewScreeen.WebViewScreen

sealed class Screens (val route: String){
    object SplashScreen: Screens("splash_screen")
    object MainScreen: Screens("main_screen")
    object WebView: Screens("web_screen")

}
@Composable
fun SetupNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screens.SplashScreen.route){
        composable(Screens.SplashScreen.route){
            SplashScreen(navController = navController)
        }
        composable(Screens.MainScreen.route){
            MainScreen(navController)
        }
        composable(Screens.WebView.route){
            WebViewScreen()
        }
    }
}
