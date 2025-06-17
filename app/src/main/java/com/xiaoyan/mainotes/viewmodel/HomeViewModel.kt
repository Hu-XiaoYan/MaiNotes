package com.xiaoyan.mainotes.viewmodel

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
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

data class UserInfo(
    val trophy: String? = null,
    val rating: String? = null,
    val friendCode: String? = null,
    val lastSyncTime: String? = null
)

class HomeViewModel : ViewModel() {
    @Composable
    fun getPlayerID(currentGameName: String): String {
        val config = GlobalConfig.read()
        //玩家数据和Token不可空, 由于玩家数据为空时落雪不返回数据, 因此Token和玩家数据都不会被保存
        return when {
            config.lxnsPersonalToken == null -> stringResource(R.string.CardModule_NotBindLxnsFc)
            currentGameName == stringResource(R.string.maidx_official) -> {
                val data = config.lxnsPlayerDataMai
                data?.name?: stringResource(R.string.CardModule_NoData)
            }
            currentGameName == stringResource(R.string.chunithm_official) -> {
                val data = config.lxnsPlayerDataChuni
                data?.name?: stringResource(R.string.CardModule_NoData)
            }
            else -> stringResource(R.string.UnknownError)
        }
    }

    @Composable
    fun checkConfig(currentGameName: String): Boolean {
        val config = GlobalConfig.read()
        when (currentGameName) {
            stringResource(R.string.maidx_official) -> {
                if (config.lxnsPlayerDataMai != null){
                    return false
                }
            }
            stringResource(R.string.chunithm_official) -> {
                if (config.lxnsPlayerDataChuni != null){
                    return false
                }
            }
        }
        return true
    }

    @Composable
    fun getPlayerData(currentGameName: String): UserInfo {
        val config = GlobalConfig.read()
        return when (currentGameName) {
            stringResource(R.string.maidx_official) -> {
                UserInfo(config.lxnsPlayerDataMai?.trophy?.name,
                    config.lxnsPlayerDataMai?.rating.toString(),
                    config.lxnsPlayerDataMai?.friendCode.toString(),
                    config.lxnsPlayerDataMai?.uploadTime)
            }
            stringResource(R.string.chunithm_official) -> {
                UserInfo(config.lxnsPlayerDataChuni?.trophy?.name,
                    config.lxnsPlayerDataChuni?.rating.toString(),
                    config.lxnsPlayerDataChuni?.friendCode.toString(),
                    config.lxnsPlayerDataChuni?.uploadTime)
            }
            else -> UserInfo()
        }
    }

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