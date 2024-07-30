package com.example.jetpackcomposebase.ui.settings.viewmodel

import android.content.Context
import androidx.navigation.NavController
import com.example.jetpackcomposebase.base.LocaleManager
import com.example.jetpackcomposebase.base.ViewModelBase
import com.example.jetpackcomposebase.navigation.NAV_SPLASH
import com.example.jetpackcomposebase.utils.MyPreference
import com.example.jetpackcomposebase.utils.PrefKey
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val localeManager: LocaleManager,
    private val myPreference: MyPreference
) : ViewModelBase() {
    fun changeLanguage(
        context: Context, languageCode: String, navController: NavController
    ) {
        localeManager.setNewLocale(context, languageCode)
        myPreference.setValueString(PrefKey.SELECTED_LANGUAGE, languageCode)
        navController.navigate(NAV_SPLASH) {
            popUpTo(navController.graph.startDestinationId) {
                inclusive = true
            }
            launchSingleTop = true
        }
    }
}

