package com.example.mobmon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.android.volley.toolbox.Volley
import com.android.volley.toolbox.StringRequest
import com.android.volley.Request

class MainActivity : AppCompatActivity() {
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
        val url = "https://www.google.com"

        // Request a string response from the provided URL.
        val stringRequest = StringRequest(Request.Method.GET, url,
                { response ->
                    // Display the first 500 characters of the response string.

                    dataTextView.text = "Response is: ${response.substring(0, 500)}"
                },
                { dataTextView.text = "That didn't work!" })

        // Add the request to the RequestQueue.
        queue.add(stringRequest)
    }
}