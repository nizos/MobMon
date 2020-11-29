package com.example.mobmon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.android.volley.toolbox.Volley
import com.android.volley.toolbox.StringRequest
import com.android.volley.Request
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    // TODO: STORE THESE IN APPLICATION SETTINGS
    private val user = "MSIAfterburner"
    private val pass = "17cc95b4017d496f82"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dataButton = findViewById<Button>(R.id.dataButton)
        dataButton?.setOnClickListener {
            getData()
        }
    }

    fun getData() {
        // Instantiate the RequestQueue.
        val dataTextView = findViewById<TextView>(R.id.dataTextView)
        val queue = Volley.newRequestQueue(this)
        val url = "http://192.168.0.199:82/mahm"  // TODO: RETRIEVE APPLICATION IP FROM SETTINGS

        // Request a string response from the provided URL.
        val stringRequest = object: StringRequest(Request.Method.GET, url,
                { response ->
                    // Display the first 500 characters of the response string.
                    dataTextView.text = "Response is: ${response.substring(0, 1500)}"
                },
                { error -> dataTextView.text = "That didn't work! - ${error.networkResponse.statusCode} - ${error.networkResponse.allHeaders}" })
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
}