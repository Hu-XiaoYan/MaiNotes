package com.xiaoyan.mainotes

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.graphics.Paint.Style
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.platform.InspectableModifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.xiaoyan.mainotes.ui.theme.MaiNotesTheme
import kotlinx.coroutines.launch
import androidx.navigation.compose.rememberNavController
import kotlin.math.exp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaiNotesTheme {
                MaiNoteMain()
            }
        }
    }
}

data object NavDestinations {
    const val SCREEN_HOME = "home"
    const val SCREEN_STORY = "story"
    const val SCREEN_SETTINGS = "settings"
    const val SCREEN_ABOUT = "about"
}
//路由名称, 用于处理selected逻辑

@Composable
fun NavigationItem(
    iconRes: Int,
    labelRes: Int,
    route: String,
    currentRoute: String?,
    onClick: () -> Unit
) {
    val label = stringResource(labelRes)
    NavigationDrawerItem(
        icon = {
            Icon(painterResource(iconRes), contentDescription = label)
        },
        label = { Text(label) },
        selected = currentRoute == route,
        onClick = onClick
    )
}
//封装好的NavigationItem, 用于侧边栏导航

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MaiNoteMain() {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    //存储drawer的打开状态
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val appBarTitle = when (currentRoute) {
        NavDestinations.SCREEN_HOME -> stringResource(R.string.nav_btn_home)
        NavDestinations.SCREEN_STORY -> stringResource(R.string.nav_btn_story)
        NavDestinations.SCREEN_SETTINGS -> stringResource(R.string.nav_btn_settings)
        NavDestinations.SCREEN_ABOUT -> stringResource(R.string.nav_btn_about)
        else -> stringResource(R.string.nav_btn_home)
    }
    //根据导航修改appBar的文本

    data class DrawerItemInfo(
        val iconRes: Int,
        val labelRes: Int,
        val route: String,
        val section: String
    )
    //数据类

    val groupedDrawerItems = listOf(
        DrawerItemInfo(
            R.drawable.nav_ic_home, R.string.nav_btn_home,
            NavDestinations.SCREEN_HOME, "main"
        ),
        DrawerItemInfo(
            R.drawable.nav_ic_story, R.string.nav_btn_story,
            NavDestinations.SCREEN_STORY, "main"
        ),
        DrawerItemInfo(
            R.drawable.nav_ic_settings, R.string.nav_btn_settings,
            NavDestinations.SCREEN_SETTINGS, "secondary"
        ),
        DrawerItemInfo(
            R.drawable.nav_ic_about, R.string.nav_btn_about,
            NavDestinations.SCREEN_ABOUT, "secondary"
        )
    )
    val mainItems = groupedDrawerItems.filter { it.section == "main" }
    val secondaryItems = groupedDrawerItems.filter { it.section == "secondary" }
    //分组渲染列表

    ModalNavigationDrawer(
        drawerState = drawerState, drawerContent = {
            ModalDrawerSheet {
                //承载DrawerItem
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    "MaiNotes Preview Ver-0.1.1",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.titleMedium
                )
                mainItems.forEach { item ->
                    NavigationItem(
                        iconRes = item.iconRes,
                        labelRes = item.labelRes,
                        route = item.route,
                        currentRoute = currentRoute,
                        onClick = {
                            scope.launch { drawerState.close() }
                            navController.navigate(item.route) {
                                launchSingleTop = true
                            }
                        }
                    )
                }
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp))
                secondaryItems.forEach { item ->
                    NavigationItem(
                        iconRes = item.iconRes,
                        labelRes = item.labelRes,
                        route = item.route,
                        currentRoute = currentRoute,
                        onClick = {
                            scope.launch { drawerState.close() }
                            navController.navigate(item.route) {
                                launchSingleTop = true
                            }
                        }
                    )
                }
            }
        }) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text(appBarTitle, maxLines = 1, overflow = TextOverflow.Ellipsis) },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                scope.launch {
                                    if (drawerState.isClosed) {
                                        drawerState.open()
                                    } else {
                                        drawerState.close()
                                    }
                                }
                            }) {
                            Icon(
                                painterResource(R.drawable.appbar_ic_menu),
                                contentDescription = "打开导航抽屉"
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = { /*TODO:About Account*/ }) {
                            Icon(
                                painterResource(R.drawable.appbar_ic_account),
                                contentDescription = "账号管理"
                            )
                        }
                    })
            }) { paddingValues ->
            NavHost(
                //此处处理导航逻辑
                navController = navController,
                startDestination = NavDestinations.SCREEN_HOME,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
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
    }
}

@Composable
fun ScreenContent(screenName: String) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("这是 $screenName 页面", style = MaterialTheme.typography.headlineMedium)
    }
}

@Preview(
    showBackground = true, uiMode = UI_MODE_NIGHT_YES, name = "DarkPreview"
)
@Preview(
    showBackground = true, name = "LightPreview"
)
@Composable
fun AllPreview() {
    MaiNotesTheme {
        MaiNoteMain()
    }
}
