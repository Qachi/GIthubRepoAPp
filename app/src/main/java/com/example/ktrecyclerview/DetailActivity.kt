package com.example.ktrecyclerview

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.net.http.SslError
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.webkit.SslErrorHandler
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.example.ktrecyclerview.databinding.ActivityDetailBinding
import com.example.ktrecyclerview.model.Item

class DetailActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =ActivityDetailBinding.inflate(layoutInflater)
        val view=binding.root
        setContentView(view)

        val actionBar = supportActionBar

        intent?.let { it ->
            val details: Item? = it.getParcelableExtra("EXTRA_DETAILS") as? Item
            details?.let {
                if (actionBar != null) {
                    actionBar.title = it.name
                }
                loadWebView(it.htmlUrl)
            }
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun loadWebView(url: String) {
        binding.webProgress.visibility = View.GONE
        binding.webView.webViewClient = object : WebViewClient() {
            @SuppressLint("WebViewClientOnReceivedSslError")
            override fun onReceivedSslError(
                view: WebView,
                handler: SslErrorHandler,
                error: SslError
            ) {
                handler.proceed()
            }

            override fun onPageStarted(
                view: WebView,
                url: String,
                favicon: Bitmap?
            ) {
                super.onPageStarted(view, url, favicon)
                binding.webProgress.visibility = View.VISIBLE
            }

            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                binding.webProgress.visibility = View.GONE
            }

            @Deprecated("Deprecated in Java")
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return true
            }

            /**
             * Notify the host application that the WebView will load the resource
             * specified by the given url.
             *
             * @param view The WebView that is initiating the callback.
             * @param url  The url of the resource the WebView will load.
             */
            override fun onLoadResource(view: WebView, url: String) {
                super.onLoadResource(view, url)
                binding.webProgress.incrementProgressBy(10)
            }
        }
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.loadUrl(url)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}