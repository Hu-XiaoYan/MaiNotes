package com.xiaoyan.mainotes

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.xiaoyan.mainotes.viewmodel.SettingsViewModel

@Composable
fun SettingsScreen() {
    var isFocused by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    var lxnsPersonalToken by remember { mutableStateOf("") }
    val viewModel: SettingsViewModel = viewModel()
    val errorMessage = viewModel.errorMessage
    val playerData = viewModel.playerData
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                focusManager.clearFocus(force = true)
            }
            .padding(16.dp)
    ) {
        Column(modifier = Modifier.fillMaxHeight()) {
            OutlinedTextField(
                value = lxnsPersonalToken,
                onValueChange = { lxnsPersonalToken = it },
                label = { Text("落雪查分器个人Token") },
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged { focusState ->
                        if (!focusState.isFocused && isFocused) {
                            Log.d("INPUT", lxnsPersonalToken)
                            viewModel.fetchLxnsPlayerData(lxnsPersonalToken)
                        }
                        isFocused = focusState.isFocused
                    }
            )
            if (errorMessage != null) {
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
            if (playerData != null) {
                Text(
                    text = "你好，${playerData.name}！当前Rating：${playerData.rating}",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
            //test
        }
    }
}

@Preview(
    showBackground = true, name = "LightSettingsPreview"
)
@Composable
fun SettingsView() {
    SettingsScreen()
}