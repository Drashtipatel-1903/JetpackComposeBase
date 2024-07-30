package com.example.jetpackcomposebase.ui.settings.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.jetpackcomposebase.R
import com.example.jetpackcomposebase.base.ToolBarData
import com.example.jetpackcomposebase.ui.settings.viewmodel.SettingViewModel
import com.example.jetpackcomposebase.utils.DebugLog
import com.example.jetpackcomposebase.utils.PrefKey

@Composable
fun SettingScreenView(
    navController: NavController,
    drawerState: DrawerState,
    bottomBarVisibility: (Boolean) -> Unit,
    topBar: (ToolBarData) -> Unit,
    circularProgress: (Boolean) -> Unit,
    settingViewModel: SettingViewModel = hiltViewModel(),
) {
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        topBar(
            ToolBarData(
                title = "Setting",
                isVisible = true,
                isDrawerIcon = true,
                isBackIconVisible = false,
                isTitleVisible = true
            )
        )
        bottomBarVisibility(true)
        circularProgress(false)
    }
    SettingUI(settingViewModel, navController)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingUI(settingViewModel: SettingViewModel, navController: NavController) {
    var selectedLanguage by remember { mutableStateOf(PrefKey.EN_CODE) }
    var expanded by remember { mutableStateOf(false) }

    // Define the language options
    val languageOptions = listOf(
        "English" to PrefKey.EN_CODE,
        "Arabic" to PrefKey.AR_CODE
    )
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(stringResource(id = R.string.lbl_setting))
        Spacer(modifier = Modifier.height(20.dp))
        // Dropdown menu for language selection
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            TextField(
                readOnly = true,
                value = languageOptions.find { it.second == selectedLanguage }?.first
                    ?: "Select Language",
                onValueChange = {},
                trailingIcon = { Icon(Icons.Filled.ArrowDropDown, null) },
                modifier = Modifier.menuAnchor()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                languageOptions.forEach { (label, code) ->
                    DropdownMenuItem(
                        text = { Text(label) },
                        onClick = {
                            selectedLanguage = code
                            DebugLog.d("Lan Code : $selectedLanguage")
                            settingViewModel.changeLanguage(context, code, navController)
                            expanded = false
                        }
                    )
                }
            }
        }

    }
}