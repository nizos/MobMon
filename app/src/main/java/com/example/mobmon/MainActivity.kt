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
import com.android.volley.Response
import org.json.JSONObject
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.InputStream

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
                { response -> parseData(response)},
                { error -> dataTextView.text = "That didn't work! - ${error.networkResponse.statusCode} -\n ${error.networkResponse.allHeaders} \n" })
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

    fun parseData(resp : String) {
        val dataTextView = findViewById<TextView>(R.id.dataTextView)

        val parser = XmlPullParserFactory.newInstance().newPullParser()
        val stream: InputStream = resp.byteInputStream()
        parser.setInput(stream, "UTF-8") // TODO: Make this encoding more customizable, not everyone uses UTF-8
        var event = parser.eventType


        while(event != XmlPullParser.END_DOCUMENT) {
            when (event) {
                XmlPullParser.START_DOCUMENT -> dataTextView.append("\nSTART!")
                XmlPullParser.END_DOCUMENT -> dataTextView.append("\nEND!")
                XmlPullParser.START_TAG -> dataTextView.append("\nTAG: ${parser.name} ")
                XmlPullParser.END_TAG -> dataTextView.append("END TAG!")
            }

            event = parser.next()
        }
    }
}