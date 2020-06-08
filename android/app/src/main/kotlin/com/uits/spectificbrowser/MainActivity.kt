package com.uits.spectificbrowser

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.annotation.NonNull
import io.flutter.app.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugins.GeneratedPluginRegistrant


class MainActivity : FlutterActivity() {

    private val CHANNEL = "flutterapp.google.com/browser"

    fun configureFlutterEngine(@NonNull flutterEngine: FlutterEngine) {
        GeneratedPluginRegistrant.registerWith(flutterEngine);
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MethodChannel(flutterView, CHANNEL).setMethodCallHandler { call, result ->
            val url = call.argument<String>("url")
            if (call.method == "openBrowser") {
                openBrowser(call, result, url!!)
            } else {
                result.notImplemented()
            }
        }
    }

    private fun openBrowser(call: MethodCall, result: Result, url: String) {
        var activity: Activity = this
        if (activity == null) {
            result.error("ACTIVITY_NOT_AVAILABLE",
                    "Browser cannot be opened without foreground activity", null)
            return
        }

        var intent = Intent(Intent.ACTION_VIEW)
        intent.setData(Uri.parse(url))

        activity.startActivity(intent)
        result.success( true as Any)
    }
}
