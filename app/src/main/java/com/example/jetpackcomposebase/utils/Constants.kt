package com.example.jetpackcomposebase.utils

import androidx.datastore.preferences.core.stringPreferencesKey
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

object Constants {
    val JSON = jacksonObjectMapper()
    var KEY_BUNDLE = "bundle"

    var KEY_GRAPH_ID = "graphId"
    var KEY_START_DESTINATION = "startDestination"
    var TERMS_CONDITION_URL = "    https://aichatapp.app/terms-of-use"
    var PRIVACY_POLICY_URL = "https://aichatapp.app/privacy-and-cookies-policy"

    var DSN = "https://e68671b99bf2afd717617c18a3aa1dc4@o4507655094140928.ingest.us.sentry.io/4507666947309568"
    const val ALL_QUOTES = "quotes"


    object DataStore {
        val SECURED_DATA = stringPreferencesKey("secured_data")
    }


    object ToolbarData {
        const val Unsubscribe: String = "Unsubscribe"

    }

}