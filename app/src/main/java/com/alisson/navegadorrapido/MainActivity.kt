package com.alisson.navegadorrapido

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        webView = WebView(this)
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.settings.databaseEnabled = true
        webView.settings.loadsImagesAutomatically = true
        webView.settings.javaScriptCanOpenWindowsAutomatically = true
        webView.settings.setSupportMultipleWindows(false)
        webView.settings.allowFileAccess = true
        webView.settings.allowContentAccess = true

        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
                return handleUrl(request.url.toString())
            }

            @Suppress("DEPRECATION")
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                return handleUrl(url)
            }
        }

        webView.loadUrl("file:///android_asset/index.html")
        setContentView(webView)
    }

    private fun handleUrl(url: String): Boolean {
        val uri = Uri.parse(url)
        val scheme = uri.scheme?.lowercase()

        return when (scheme) {
            "http", "https" -> {
                webView.loadUrl(url)
                true
            }
            "mailto", "tel", "sms", "geo" -> {
                startActivity(Intent(Intent.ACTION_VIEW, uri))
                true
            }
            "intent" -> {
                try {
                    startActivity(Intent.parseUri(url, Intent.URI_INTENT_SCHEME))
                } catch (_: Exception) {
                }
                true
            }
            else -> false
        }
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) webView.goBack() else super.onBackPressed()
    }

    override fun onDestroy() {
        webView.destroy()
        super.onDestroy()
    }
}
