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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.xiaoyan.mainotes.R

@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        HomeAccountCard_Test(
            gameName = "舞萌DX", playerId = "XiaoYan#0125",
            description = "「陰険」で 「強欲」で 「滑稽」で 「外道」", rating = "14816",
            playerNumId = "123456789", lastSyncDate = "2025/4/8 01:17:01"
        )
        HomeAccountCard_Test(
            gameName = "中二节奏", playerId = "YaoHuo#0125",
            description = "THE ACHIEVER／RATING 13.00", rating = "13.06",
            playerNumId = "123456789", lastSyncDate = "2025/4/8 01:17:01"
        )
    }
}

@Composable
fun HomeAccountCard_Test(
    gameName: String = stringResource(R.string.CardModuleMai_GetInfoFiled),
    playerId: String = stringResource(R.string.CardModuleMai_NotBindLxnsFc),
    playerIconRes: Int = R.drawable.card_ic_icon_dummy,
    description: String = stringResource(R.string.CardModuleMai_GetInfoFiled),
    rating: String = stringResource(R.string.CardModuleMai_GetInfoFiled),
    playerNumId: String = stringResource(R.string.CardModuleMai_GetInfoFiled),
    lastSyncDate: String = stringResource(R.string.CardModuleMai_GetInfoFiled)
) {
    var expanded by remember { mutableStateOf(false) }
    val rotateAngle by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMediumLow
        )
    )
    OutlinedCard(
        modifier = Modifier
            .padding(8.dp)
            .clip(CardDefaults.shape)
            .border(1.dp, MaterialTheme.colorScheme.outline, CardDefaults.shape),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Text(gameName, style = MaterialTheme.typography.titleLarge)
                    Text(playerId, style = MaterialTheme.typography.titleSmall)
                }
                //卡片概览(游戏/登录状态)
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        painterResource(R.drawable.card_ic_arrow),
                        contentDescription = if (expanded) stringResource(R.string.desc_card_close)
                        else stringResource(R.string.desc_card_open),
                        modifier = Modifier.rotate(rotateAngle)
                    )
                }
                //展开卡片箭头
            }
        }
        AnimatedVisibility(
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
                HomeAccCardMai(playerIconRes,description,rating,playerNumId,lastSyncDate)
                Spacer(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp))
            }
        }
    }
}

@Composable
fun HomeAccCardMai(
    playerIconRes: Int,
    description: String,
    rating: String,
    playerNumId: String,
    lastSyncDate: String
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
                    contentDescription = stringResource(R.string.CardModuleMai_PlayerIconDesc),
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
                    stringResource(R.string.CardModuleMai_FriendCode) + playerNumId,
                    50
                )
                MarqueeText_FullWidth(
                    stringResource(R.string.CardModuleMai_LastSyncTime) + lastSyncDate,
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
    speed: Int
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