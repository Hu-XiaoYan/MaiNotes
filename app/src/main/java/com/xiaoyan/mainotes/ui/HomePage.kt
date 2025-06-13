package com.xiaoyan.mainotes.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.xiaoyan.mainotes.R
import com.xiaoyan.mainotes.viewmodel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    val viewModel: HomeViewModel = viewModel()
    val (isRefreshing, onRefresh) = viewModel.onHomeRefreshing()
    //获取回传的刷新状态和方法

    PullToRefreshBox(
        isRefreshing = isRefreshing, onRefresh = onRefresh, modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())
        ) {
            HomeAccountCardMaiChu(gameName = "舞萌DX")
            HomeAccountCardMaiChu(gameName = "中二节奏")
        }
    }
}

@Composable
fun HomeAccountCardMaiChu(
    gameName: String = stringResource(R.string.unknown_game),
    playerIcon: Painter = painterResource(R.drawable.card_ic_icon_dummy),
    //临时参数, 后续需要对接真实玩家头像
    trophy: String = stringResource(R.string.CardModule_GetInfoFiled),
    rating: String = stringResource(R.string.CardModule_GetInfoFiled),
    friendCode: String = stringResource(R.string.CardModule_GetInfoFiled),
    lastSyncDate: String = stringResource(R.string.CardModule_GetInfoFiled)
) {
    var expanded by remember { mutableStateOf(false) }
    val isLockedCard by remember { mutableStateOf(false) }
    val viewModel: HomeViewModel = viewModel()
    val playerName = viewModel.getPlayerID(gameName)
    val userInfo = viewModel.getPlayerData(gameName)

    OutlinedCard(modifier = Modifier
        .padding(horizontal = 16.dp, vertical = 8.dp)
        .clip(CardDefaults.shape)
        .clickable { if (!isLockedCard) { expanded = !expanded } }
        .border(1.dp, MaterialTheme.colorScheme.outline, CardDefaults.shape),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()
                //自适应玩家信息
            ) {
                Column { Text(gameName, style = MaterialTheme.typography.titleLarge)
                    Text(playerName, style = MaterialTheme.typography.titleSmall)
                }
                //卡片概览(游戏/登录状态)
                viewModel.HomeCardStatus(isLockedCard, expanded)
                //卡片状态图标
            }
            AnimatedVisibility(visible = expanded,
                enter = expandVertically(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessMediumLow
                    )
                ), exit = shrinkVertically(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessMediumLow
                    )
                )
            ) {
                Row(modifier = Modifier.height(IntrinsicSize.Min)) {
                    Column {
                        Image(playerIcon,
                            contentDescription = stringResource(R.string.CardModule_PlayerIconDesc),
                            modifier = Modifier.size(96.dp).clip(RoundedCornerShape(12.dp)))}
                    Column(
                        modifier = Modifier.padding(bottom = 3.dp, start = 16.dp, end = 16.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        viewModel.HomeAccCardMarqueeText(userInfo.trophy ?: trophy)
                        viewModel.HomeAccCardMarqueeText("Rating:${userInfo.rating ?: rating}")
                        viewModel.HomeAccCardMarqueeText(
                            (stringResource(R.string.CardModule_FriendCode) +
                                    (userInfo.friendCode ?: friendCode)))
                        viewModel.HomeAccCardMarqueeText(
                            stringResource(R.string.CardModule_LastSyncTime) +
                                    (userInfo.lastSyncTime ?: lastSyncDate))
                    }
                }
            }
        }
    }
}