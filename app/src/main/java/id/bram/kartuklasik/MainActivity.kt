package id.bram.kartuklasik

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient

class MainActivity : Activity() {
    private lateinit var web: WebView

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val grey = Color.rgb(192, 192, 192)
        window.statusBarColor = grey
        window.navigationBarColor = grey
        var flags = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        if (Build.VERSION.SDK_INT >= 26) flags = flags or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        @Suppress("DEPRECATION")
        window.decorView.systemUiVisibility = flags

        web = WebView(this)
        web.setBackgroundColor(Color.rgb(0, 128, 0))
        web.overScrollMode = View.OVER_SCROLL_NEVER
        web.isHapticFeedbackEnabled = false
        web.isLongClickable = false
        web.setOnLongClickListener { true }
        with(web.settings) {
            javaScriptEnabled = true
            domStorageEnabled = true
            @Suppress("DEPRECATION")
            allowFileAccess = true
            textZoom = 100
            setSupportZoom(false)
        }
        web.webViewClient = WebViewClient()
        setContentView(web)
        web.loadUrl("file:///android_asset/index.html")
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        web.evaluateJavascript("window.kkBack ? window.kkBack() : false") { r ->
            if (r != "true") moveTaskToBack(true)
        }
    }

    override fun onPause() {
        web.evaluateJavascript("window.kkSave && window.kkSave()", null)
        super.onPause()
    }
}
