package com.xiaoyan.mainotes.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.xiaoyan.mainotes.R
import com.xiaoyan.mainotes.core.GlobalConfig
import com.xiaoyan.mainotes.viewmodel.SettingsViewModel

@Composable
fun SettingsScreen() {
    var isFocused by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    var lxnsPersonalToken by remember { mutableStateOf("") }
    val viewModel: SettingsViewModel = viewModel()
    val errorMessage = viewModel.errorMessage
    val notBind = stringResource(R.string.CardModule_NotBindLxnsFc)

    val isInPreview = LocalInspectionMode.current
    if (!isInPreview) {
        LaunchedEffect(Unit) {
            val config = GlobalConfig.read()
            lxnsPersonalToken = if (config.lxnsPersonalToken == null) {
                notBind
            } else {
                config.lxnsPersonalToken.toString()
            }
        }
    }
    //Page启动前动作

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
        OutlinedTextField(
            value = lxnsPersonalToken,
            onValueChange = { lxnsPersonalToken = it },
            label = { Text(stringResource(R.string.InputLxnsPersonalToken)) },
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { focusState ->
                    if (!focusState.isFocused && isFocused) {
                        viewModel.fetchLxnsPlayerData(lxnsPersonalToken)
                    }
                    isFocused = focusState.isFocused
                },
            isError = errorMessage != null,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                }
            ),
            supportingText = {
                if (errorMessage != null) {
                    when (errorMessage) {
                        "TokenEmpty" -> {
                            Text(
                                text = stringResource(R.string.TokenEmpty),
                                color = MaterialTheme.colorScheme.error
                            )
                        }

                        "unauthorized" -> {
                            Text(
                                text = stringResource(R.string.TokenError),
                                color = MaterialTheme.colorScheme.error
                            )
                        }

                        else -> {
                            Text(
                                text = "${stringResource(R.string.Error)}:${errorMessage}",
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                }
            }
        )
    }
}

@Preview(
    showBackground = true, name = "LightSettingsPreview"
)
@Composable
fun SettingsView() {
    SettingsScreen()
}