package com.xiaoyan.mainotes.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.MarqueeAnimationMode
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.xiaoyan.mainotes.R
import com.xiaoyan.mainotes.viewmodel.GlobalConfig
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    var isRefreshing by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val onRefresh = {
        isRefreshing = true
        coroutineScope.launch {
            delay(5000)
            isRefreshing = false
        }
        Unit
    }
    var test by remember { mutableStateOf("测试读取") }

    val isInPreview = LocalInspectionMode.current
    if (!isInPreview) {
        LaunchedEffect(Unit) {
            val config = GlobalConfig.read()
            test = if (config.playerData?.lxnsPlayerDataMai == null) {
                "测试读取完毕--信息为空"
            } else {
                "测试读取完毕--有信息"
            }
        }
    }

    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            HomeAccCard(gameName = "舞萌DX")
            HomeAccCard(gameName = "中二节奏")
            Text(text = test)
        }
    }
}

@Composable
fun HomeAccCard(
    gameName: String = stringResource(R.string.CardModule_GetInfoFiled),
    playerId: String = stringResource(R.string.CardModule_NotBindLxnsFc),
    playerIconRes: Int = R.drawable.card_ic_icon_dummy,
    description: String = stringResource(R.string.CardModule_GetInfoFiled),
    rating: String = stringResource(R.string.CardModule_GetInfoFiled),
    playerNumId: String = stringResource(R.string.CardModule_GetInfoFiled),
    lastSyncDate: String = stringResource(R.string.CardModule_GetInfoFiled),
    isLockedCard: Boolean = true,
) {
    var expanded by remember { mutableStateOf(false) }
    val rotateAngle by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMediumLow
        )
    )

    LaunchedEffect(Unit) { }

    OutlinedCard(
        modifier = Modifier
            .padding(8.dp)
            .clip(CardDefaults.shape)
            .clickable {
                if (!isLockedCard) {
                    expanded = !expanded
                }
            }
            .border(1.dp, MaterialTheme.colorScheme.outline, CardDefaults.shape),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
                //自适应玩家信息
            ) {
                Column {
                    Text(gameName, style = MaterialTheme.typography.titleLarge)
                    Text(playerId, style = MaterialTheme.typography.titleSmall)
                }
                //卡片概览(游戏/登录状态)
                if (isLockedCard) {
                    Icon(
                        painterResource(R.drawable.card_ic_locked),
                        contentDescription = stringResource(R.string.desc_card_locked)
                    )
                    //锁定卡片
                } else {
                    Icon(
                        painterResource(R.drawable.card_ic_arrow),
                        contentDescription = if (expanded) stringResource(R.string.desc_card_close)
                        else stringResource(R.string.desc_card_open),
                        modifier = Modifier.rotate(rotateAngle)
                    )
                    //展开卡片箭头
                }
            }
        }
        AnimatedVisibility(
            //卡片展开动画
            visible = expanded,
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
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                HomeAccCardModuleMaiChu(
                    playerIconRes,
                    description,
                    rating,
                    playerNumId,
                    lastSyncDate
                )
                Spacer(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp))
            }
        }
    }
}

@Composable
fun HomeAccCardModuleMaiChu(
    playerIconRes: Int,
    description: String,
    rating: String,
    playerNumId: String,
    lastSyncDate: String,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .height(IntrinsicSize.Min)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
            )
            {
                Image(
                    painterResource(playerIconRes),
                    contentDescription = stringResource(R.string.CardModule_PlayerIconDesc),
                    modifier = Modifier
                        .size(96.dp)
                        .clip(RoundedCornerShape(12.dp))
                )
            }
            //玩家头像/ID列
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(bottom = 3.dp, start = 16.dp, end = 16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            )
            {
                MarqueeText_FullWidth(description, 50)
                MarqueeText_FullWidth(rating, 50)
                MarqueeText_FullWidth(
                    stringResource(R.string.CardModule_FriendCode) + playerNumId,
                    50
                )
                MarqueeText_FullWidth(
                    stringResource(R.string.CardModule_LastSyncTime) + lastSyncDate,
                    50
                )
            }
            //详细信息列
        }
        //玩家信息行
    }
}

@Composable
fun MarqueeText_FullWidth(
    text: String,
    speed: Int,
) {
    Text(
        text,
        modifier = Modifier
            .fillMaxWidth()
            .basicMarquee(
                iterations = Int.MAX_VALUE,
                velocity = speed.dp,
                repeatDelayMillis = 1000,
                animationMode = MarqueeAnimationMode.Immediately
            )
    )
}

@Preview(
    showBackground = true, name = "LightHomePreview"
)
@Composable
fun HomePreView() {
    HomeScreen()
}