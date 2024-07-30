package com.example.jetpackcomposebase

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.jetpackcomposebase.base.LocaleManager
import com.example.jetpackcomposebase.utils.DebugLog
import com.example.jetpackcomposebase.utils.MyPreference
import com.example.jetpackcomposebase.utils.PrefKey
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var localeManager: LocaleManager

    @Inject
    lateinit var mPref: MyPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackBaseApp(localeManager)
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        localeManager.setLocale(this)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        val languageCode = mPref.getValueString(PrefKey.SELECTED_LANGUAGE, PrefKey.EN_CODE)
        window.decorView.layoutDirection =
            if (languageCode!!.contains(PrefKey.AR_CODE)) View.LAYOUT_DIRECTION_RTL else View.LAYOUT_DIRECTION_LTR
    }

    override fun attachBaseContext(base: Context) {
        DebugLog.e("attachBaseContext")
        // super.attachBaseContext(base);
        val languageCode = MyApp.applicationContext().mPref.getValueString(
            PrefKey.SELECTED_LANGUAGE,
            PrefKey.EN_CODE
        )
        useCustomConfig(base, languageCode)?.let { super.attachBaseContext(it) }
    }

    @SuppressLint("ObsoleteSdkInt")
    private fun useCustomConfig(context: Context, languageCode: String?): Context? {
        Locale.setDefault(Locale(languageCode!!))
        return if (Build.VERSION.SDK_INT >= 17) {
//            val config = Configuration()
            val res: Resources = context.resources
            val config =
                Configuration(res.configuration)
            config.setLocale(Locale(languageCode))
            context.createConfigurationContext(config)
        } else {
            val res: Resources = context.resources
            val config =
                Configuration(res.configuration)
            config.locale = Locale(languageCode)
            res.updateConfiguration(config, res.displayMetrics)
            context
        }
    }

}
