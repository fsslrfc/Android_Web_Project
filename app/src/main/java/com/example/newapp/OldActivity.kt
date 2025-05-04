package com.example.newapp

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient

class OldActivity : Activity() {
	private lateinit var mWebView: WebView
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.layout_old_start)
		init_webview()
	}
	
	override fun onBackPressed() {
		if (mWebView.canGoBack()) {
			mWebView.goBack()
		}
		else {
			super.onBackPressed()
		}
	}
	
	@SuppressLint("SetJavaScriptEnabled")
	private fun init_webview() {
		mWebView = findViewById(R.id.main_web_view)
		mWebView.apply {
			settings.apply {
				javaScriptEnabled = true
				useWideViewPort  = true
				loadWithOverviewMode = true
			}
			webViewClient = WebViewClient()
			loadUrl("https://mis.bjtu.edu.cn")
		}
	}
}