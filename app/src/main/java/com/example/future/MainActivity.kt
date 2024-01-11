package com.example.future

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.imaginativeworld.oopsnointernet.callbacks.ConnectionCallback
import org.imaginativeworld.oopsnointernet.dialogs.pendulum.NoInternetDialogPendulum


class MainActivity : AppCompatActivity() {
    lateinit var webview: WebView
    lateinit var noInternet: TextView
    lateinit var noLayout: LinearLayout

    @SuppressLint("MissingInflatedId", "SetJavaScriptEnabled", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        noInternet = findViewById(R.id.noInternet)
        webview = findViewById(R.id.webview)
        noLayout = findViewById(R.id.noLayout)

        NoInternetDialogPendulum.Builder(
            this,
            lifecycle
        ).apply {
            dialogProperties.apply {
                connectionCallback = object : ConnectionCallback { // Optional
                    override fun hasActiveConnection(hasActiveConnection: Boolean) {
                        try {


                            if (hasActiveConnection) {
                                runOnUiThread {
                                    webview.setVisibility(View.VISIBLE);
                                    noLayout.setVisibility(View.GONE);
                                    webview.loadUrl("https://thafutureplaning.com")
                                    webview.settings.javaScriptEnabled = true
                                    webview.webViewClient = object : WebViewClient() {
                                        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                                            if (url != null && url.startsWith("https://meet.google.com")) {
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


                            } else {
                                runOnUiThread {

                                    webview.setVisibility(View.GONE);
                                    noLayout.setVisibility(View.VISIBLE);
                                }

                            }
                        }catch ( e:Exception){
                            Toast.makeText(this@MainActivity,e.message,Toast.LENGTH_LONG).show()

                        }



                    }
                }
                cancelable = false // Optional
                noInternetConnectionTitle = "No Internet" // Optional
                noInternetConnectionMessage =
                    "Check your Internet connection and try again." // Optional
                showInternetOnButtons = true // Optional
                pleaseTurnOnText = "Please turn on" // Optional
                wifiOnButtonText = "Wifi" // Optional
                mobileDataOnButtonText = "Mobile data" // Optional

                onAirplaneModeTitle = "No Internet" // Optional
                onAirplaneModeMessage = "You have turned on the airplane mode." // Optional
                pleaseTurnOffText = "Please turn off" // Optional
                airplaneModeOffButtonText = "Airplane mode" // Optional
                showAirplaneModeOffButtons = true // Optional
            }
        }.build()

    }
}