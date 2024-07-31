package com.example.jetpackcomposebase.ui.settings.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.jetpackcomposebase.MyApp
import com.example.jetpackcomposebase.R
import com.example.jetpackcomposebase.base.ToolBarData
import com.example.jetpackcomposebase.navigation.NAV_SPLASH
import com.example.jetpackcomposebase.ui.custom_compose.CustomButton
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
    settingViewModel: SettingViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val application = context.applicationContext as MyApp
    val title = stringResource(id = R.string.lbl_setting)  // Get the title from string resources

    LaunchedEffect(Unit) {
        topBar(
            ToolBarData(
                title = title,
                isVisible = true,
                isDrawerIcon = true,
                isBackIconVisible = false,
                isTitleVisible = true
            )
        )
        bottomBarVisibility(true)
        circularProgress(false)
    }
    SettingUI(settingViewModel, navController, onLanguageChange = { localeCode ->
        application.updateLocale(context, localeCode)
    })
}

@Composable
fun SettingUI(
    settingViewModel: SettingViewModel,
    navController: NavController,
    onLanguageChange: (String) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(stringResource(id = R.string.lbl_setting))
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CustomButton(
                text = stringResource(id = R.string.lbl_english),
                backgroundColor = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .padding(10.dp)
                    .weight(1f),
                onCLick = {
                    onLanguageChange(PrefKey.EN_CODE)
                }
            )
            CustomButton(
                text = stringResource(id = R.string.lbl_arabic),
                backgroundColor = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .padding(10.dp)
                    .weight(1f),
                onCLick = {
                    onLanguageChange(PrefKey.AR_CODE)
                }
            )

        }
    }
}

