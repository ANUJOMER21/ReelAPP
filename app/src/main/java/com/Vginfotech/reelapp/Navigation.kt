package com.Vginfotech.reelapp

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.Vginfotech.reelapp.CategorySelector
import com.Vginfotech.reelapp.LoginRegister.OtpPage
import com.Vginfotech.reelapp.cideo.AddVideo
import com.Vginfotech.reelapp.cideo.ReelsScreen
import compose.material.theme.LoginPage
import compose.material.theme.RegisterPage
enum class Navigation(val route: String) {
    Login("login_page"),
    Register("register_page"),
    Reels("reels_page"),
    Profile("profile_page"),
    AddVideo("AddVideo_page"),
    OtpPage("otp_page"),
    CategorySelector("category_selector")
}
@Composable
fun NavigationPage(isalreadyLogin:Boolean=false){
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination =
        if(isalreadyLogin) Navigation.Reels.route else Navigation.Login.route,
        builder = {
        composable(Navigation.Login.route, content = { LoginPage(navController = navController) })
        composable(Navigation.Register.route, content = { RegisterPage(navController = navController) })
        composable(Navigation.Reels.route, content = { ReelsScreen(navController=navController) })
        composable(Navigation.Profile.route, content = { ProfilePage(navController=navController) })
        composable(Navigation.AddVideo.route, content = { AddVideo(navController=navController) })
        composable(Navigation.OtpPage.route, content = { OtpPage(navController=navController) })
        composable(Navigation.CategorySelector.route, content = { CategorySelector(navController=navController) })


    })
}