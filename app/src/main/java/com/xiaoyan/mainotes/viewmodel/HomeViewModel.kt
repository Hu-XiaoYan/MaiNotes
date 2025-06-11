package com.xiaoyan.mainotes.viewmodel

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.MarqueeAnimationMode
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.xiaoyan.mainotes.R
import com.xiaoyan.mainotes.core.GlobalConfig
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    fun getCardSubTitle(
        currentGameName: String,
        gameNameStringMai: String,
        gameNameStringChuni: String,
    ): String {
        val config = GlobalConfig.getUserConfig().copy()
        //玩家数据和Token不可空, 由于玩家数据为空时落雪不返回数据, 因此Token和玩家数据都不会被保存
        return when {
            config.lxnsPersonalToken == null -> "NotBind"
            currentGameName == gameNameStringMai -> {
                val data = config.lxnsPlayerDataMai
                data?.name ?: "NoData"
            }

            currentGameName == gameNameStringChuni -> {
                val data = config.lxnsPlayerDataChuni
                data?.name ?: "NoData"
            }

            else -> "UnknownError"
        }
    }

    //^^^上方代码可能需要重构

    @Composable
    fun onHomeRefreshing(): Pair<Boolean, () -> Unit> {
        var isRefreshing by remember { mutableStateOf(false) }
        val scope = rememberCoroutineScope()
        val onRefresh = {
            isRefreshing = true
            scope.launch {
                delay(5000)
                isRefreshing = false
            }
            Unit
        }
        return Pair(isRefreshing, onRefresh)
    }
    //主页下拉刷新动作

    @Composable
    fun HomeCardStatus(status: Boolean, expanded: Boolean) {
        val rotateAngle by animateFloatAsState(
            targetValue = if (expanded) 180f else 0f,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessMediumLow
            )
        )
        when{
            status -> Icon(painterResource(R.drawable.card_ic_locked),
                contentDescription = stringResource(R.string.desc_card_locked)
            )
            else -> Icon(
                painterResource(R.drawable.card_ic_arrow),
                contentDescription = if (expanded) stringResource(R.string.desc_card_close)
                else stringResource(R.string.desc_card_open),
                modifier = Modifier.rotate(rotateAngle)
            )
        }
    }

    @Composable
    fun HomeAccCardMarqueeText(text: String) {
        Text(text, modifier = Modifier.fillMaxWidth()
            .basicMarquee(iterations = Int.MAX_VALUE, velocity = 50.dp, repeatDelayMillis = 1000,
                animationMode = MarqueeAnimationMode.Immediately))
    }
}