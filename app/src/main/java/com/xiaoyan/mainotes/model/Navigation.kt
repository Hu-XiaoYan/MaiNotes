package com.xiaoyan.mainotes.model

import com.xiaoyan.mainotes.R

val groupedDrawerItems = listOf(
    DrawerItemInfo(
        R.drawable.nav_ic_home,
        R.string.nav_btn_home,
        NavDestinations.SCREEN_HOME,
        "main"
    ),
    DrawerItemInfo(
        R.drawable.nav_ic_story,
        R.string.nav_btn_story,
        NavDestinations.SCREEN_STORY,
        "main"
    ),
    DrawerItemInfo(
        R.drawable.nav_ic_settings,
        R.string.nav_btn_settings,
        NavDestinations.SCREEN_SETTINGS,
        "secondary"
    ),
    DrawerItemInfo(
        R.drawable.nav_ic_about,
        R.string.nav_btn_about,
        NavDestinations.SCREEN_ABOUT,
        "secondary"
    )
)
val mainItems = groupedDrawerItems.filter { it.section == "main" }
val secondaryItems = groupedDrawerItems.filter { it.section == "secondary" }
//分组渲染

data object NavDestinations {
    const val SCREEN_HOME = "home"
    const val SCREEN_STORY = "story"
    const val SCREEN_SETTINGS = "settings"
    const val SCREEN_ABOUT = "about"
}
//路由名称, 用于处理selected逻辑

data class DrawerItemInfo(
    val iconRes: Int,
    val labelRes: Int,
    val route: String,
    val section: String,
)
//DrawerItem数据类