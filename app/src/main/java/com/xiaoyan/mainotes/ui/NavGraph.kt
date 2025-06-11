package com.xiaoyan.mainotes.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.xiaoyan.mainotes.R
import com.xiaoyan.mainotes.model.NavDestinations

@Composable
fun MaiNotesNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
){
    NavHost(
        navController = navController,
        startDestination = NavDestinations.SCREEN_HOME,
        modifier = modifier
    ) {
        composable(NavDestinations.SCREEN_HOME) {
            HomeScreen()
        }
        composable(NavDestinations.SCREEN_STORY) {
            ScreenContent(screenName = NavDestinations.SCREEN_STORY)
        }
        composable(NavDestinations.SCREEN_SETTINGS) {
            SettingsScreen()
        }
        composable(NavDestinations.SCREEN_ABOUT) {
            ScreenContent(screenName = NavDestinations.SCREEN_ABOUT)
        }
    }
}

@Composable
fun resolveAppBarTitle(currentRoute: String?): String {
    return when (currentRoute) {
        NavDestinations.SCREEN_HOME -> stringResource(R.string.nav_btn_home)
        NavDestinations.SCREEN_STORY -> stringResource(R.string.nav_btn_story)
        NavDestinations.SCREEN_SETTINGS -> stringResource(R.string.nav_btn_settings)
        NavDestinations.SCREEN_ABOUT -> stringResource(R.string.nav_btn_about)
        else -> stringResource(R.string.Error)
    }
}