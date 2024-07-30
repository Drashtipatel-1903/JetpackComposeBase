package com.example.jetpackcomposebase

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import com.example.jetpackcomposebase.base.LocaleManager
import com.example.jetpackcomposebase.utils.Constants.DSN
import com.example.jetpackcomposebase.utils.MyPreference
import com.example.jetpackcomposebase.utils.SentryService
import dagger.hilt.android.HiltAndroidApp
import java.util.Locale
import javax.inject.Inject

@HiltAndroidApp
class MyApp : Application() {
    @Inject
    lateinit var mPref: MyPreference

    @Inject
    lateinit var localeManager: LocaleManager

    companion object {
        private var instance: MyApp? = null
        fun applicationContext(): MyApp {
            return instance as MyApp
        }
    }

    fun updateLocale(context: Context, localeCode: String) {
        val locale = Locale(localeCode)
        Locale.setDefault(locale)
        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
    }

    override fun onCreate() {
        super.onCreate()
        //localeManager.setLocale(this)
        // You need to add your own dsn for sentry log
        SentryService.init(DSN)
        instance = this
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        localeManager.setLocale(this)
    }




    init {
        instance = this
    }
}