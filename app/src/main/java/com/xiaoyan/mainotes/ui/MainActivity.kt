package com.xiaoyan.mainotes.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.xiaoyan.mainotes.R
import com.xiaoyan.mainotes.model.mainItems
import com.xiaoyan.mainotes.model.secondaryItems
import com.xiaoyan.mainotes.ui.theme.MaiNotesTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaiNotesTheme {
                MaiNotesMain()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MaiNotesMain() {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val appBarTitle = resolveAppBarTitle(currentRoute)

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    stringResource(R.string.nav_title),
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
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(appBarTitle, maxLines = 1, overflow = TextOverflow.Ellipsis) },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                scope.launch {
                                    when {
                                        drawerState.isClosed -> drawerState.open()
                                        else -> drawerState.close()
                                    }
                                }
                            }) {
                            Icon(
                                painterResource(R.drawable.appbar_ic_menu),
                                contentDescription =
                                    if (drawerState.isClosed) stringResource(R.string.desc_nav_open)
                                    else stringResource(R.string.desc_nav_close)
                            )
                        }
                    })
            }) { paddingValues ->
            MaiNotesNavGraph(
                navController = navController,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            )
        }
    }
}

@Composable
fun NavigationItem(
    iconRes: Int,
    labelRes: Int,
    route: String,
    currentRoute: String?,
    onClick: () -> Unit,
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