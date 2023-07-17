package ru.pakarpichev.myapplication.presentation.screens.webViewScreeen

import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.onesignal.OneSignal


@Composable
fun WebViewScreen() {
    val context = LocalContext.current
    OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)
    OneSignal.initWithContext(context)
    OneSignal.setAppId("afa67b5d-5912-4f53-b89b-9d7514e5b663")
    OneSignal.promptForPushNotifications()
    AndroidView(factory = {
        WebView(it).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
            )
            webViewClient = WebViewClient()
            loadUrl("https://www.sports.ru/football/")
        }
    })
}
