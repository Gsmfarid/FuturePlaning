package com.example.future

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    lateinit var webview:WebView
    lateinit var noInternet:TextView
    @SuppressLint("MissingInflatedId", "SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        noInternet = findViewById(R.id.noInternet)
        webview = findViewById(R.id.webview)
        webview.loadUrl("https://thafutureplaning.com/dashboard/index.php")
        webview.settings.javaScriptEnabled = true
        webview.webViewClient = object :  WebViewClient(){
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                if(url != null && url.startsWith("https://meet.google.com")){
                    val webeIntent = Intent(Intent.ACTION_VIEW)
                    webeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    webeIntent.setData(Uri.parse(url))
                   startActivity(webeIntent)
                    return true
                }
                return false
            }
        }

    }
}