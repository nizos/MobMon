package com.example.mobmon.controller

import android.os.Handler
import android.os.Looper
import android.util.Base64
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.MutableLiveData
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.mobmon.MainActivity
import com.example.mobmon.R
import com.example.mobmon.data.MSIParser

object MainController {
    var queue: RequestQueue = Volley.newRequestQueue(MainActivity.appContext)
    var mainHandler: Handler = Handler(Looper.getMainLooper())

    var metricsData = MutableLiveData<MutableMap<String, MutableMap<String,String>>>()

    fun connect(address: String, username: String, password: String, interval: String) {
        this.mainHandler?.post(object : Runnable {
            override fun run() {
                update(address, username, password)
                // Log.v("mobmon/connect", "Connecting to $address in $interval (ms) with the username [$username]")
                // Log.v("mobmon/metricsData", "MetricsData is ${metricsData.value?.size} entries big.")
                this@MainController.mainHandler.postDelayed(this, interval.toLong())
            }
        })
    }

    private fun update(ip: String, user: String, pass: String) {
        val stringRequest = object: StringRequest(
                Method.GET, ip,
                { response -> metricsData.apply { value = MSIParser.parseMSIData(response, "UTF-8") } }, // TODO: Make encoding more dynamic.
                { error -> metricsData.apply { Log.e("mobmon/connect", error.toString()) } })
        {
            override fun getHeaders() : MutableMap<String,String> {
                val headers = HashMap<String, String>()
                val credentials = String.format("%s:%s", user, pass) // TODO: FORCE THIS TO USE APPLICATION SETTINGS FOR RETRIEVING USER/PASS
                val auth = Base64.encodeToString(credentials.toByteArray(), Base64.NO_WRAP)
                headers["Authorization"] = "Basic $auth"
                return headers
            }
        }
        // Add the request to the RequestQueue.
        queue.add(stringRequest)
    }

    fun stringifyReturn(input: MutableMap<String, MutableMap<String,String>>): String {
        var returnString = ""
        input.forEach {
            returnString += it.key + "\n"
            it.value.forEach {
                returnString += "-> " + it.key + " = " + it.value + "\n"
            }
        }
        return returnString
    }
}