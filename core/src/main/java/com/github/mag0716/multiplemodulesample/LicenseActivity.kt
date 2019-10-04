package com.github.mag0716.multiplemodulesample

import android.os.Bundle
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity

class LicenseActivity : AppCompatActivity() {

    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_license)

        webView = findViewById(R.id.web_view)
        webView.loadUrl("file:///android_asset/licenses.html")
    }
}