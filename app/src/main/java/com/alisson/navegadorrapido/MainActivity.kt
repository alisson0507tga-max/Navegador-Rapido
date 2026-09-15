package com.alisson.navegadorrapido

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        webView = findViewById(R.id.browser_webview)
        configureWebView()

        if (savedInstanceState == null) loadHomePage()
        else webView.restoreState(savedInstanceState)

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (webView.canGoBack()) webView.goBack() else finish()
            }
        })
    }

    private fun configureWebView() {
        webView.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
            databaseEnabled = true
            loadsImagesAutomatically = true
            javaScriptCanOpenWindowsAutomatically = true
            setSupportMultipleWindows(false)
            allowFileAccess = false
            allowContentAccess = false
            builtInZoomControls = false
            displayZoomControls = false
        }

        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
                return handleUrl(request.url)
            }

            @Suppress("DEPRECATION")
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                return handleUrl(Uri.parse(url))
            }
        }

    }

    private fun handleUrl(uri: Uri): Boolean {
        return when (uri.scheme?.lowercase()) {
            "http", "https" -> {
                webView.loadUrl(uri.toString())
                true
            }
            "mailto", "tel", "sms", "geo" -> {
                runCatching { startActivity(Intent(Intent.ACTION_VIEW, uri)) }
                true
            }
            "intent" -> {
                runCatching { startActivity(Intent.parseUri(uri.toString(), Intent.URI_INTENT_SCHEME)) }
                true
            }
            else -> false
        }
    }

    private fun loadHomePage() {
        val html = assets.open("index.html").bufferedReader(Charsets.UTF_8).use { it.readText() }
        webView.loadDataWithBaseURL(
            HOME_BASE_URL,
            html,
            "text/html",
            "UTF-8",
            null
        )
    }

    override fun onSaveInstanceState(outState: Bundle) {
        webView.saveState(outState)
        super.onSaveInstanceState(outState)
    }

    override fun onDestroy() {
        webView.stopLoading()
        webView.destroy()
        super.onDestroy()
    }

    companion object {
        private const val HOME_BASE_URL = "https://navegador-rapido.local/"
    }
}
