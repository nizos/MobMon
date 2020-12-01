package com.example.mobmon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.SystemClock.sleep
import android.text.method.ScrollingMovementMethod
import android.util.Base64
import android.widget.Button
import android.widget.TextView
import com.android.volley.toolbox.Volley
import com.android.volley.toolbox.StringRequest
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.example.mobmon.data.MSIParser

class MainActivity : AppCompatActivity() {

    private var ipAddress: String = "http://192.168.87.128:82/mahm"
    private var username: String = "MSIAfterburner"
    private var password: String = "17cc95b4017d496f82"
    private var interval: Long = 1000
    lateinit var mainHandler: Handler
    lateinit var dataTextView: TextView
    lateinit var queue: RequestQueue


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val ipAddressTextField = findViewById<TextView>(R.id.IpAddressTextField)
        val usernameTextField = findViewById<TextView>(R.id.UsernameTextField)
        val passwordTextField = findViewById<TextView>(R.id.PasswordTextField)
        val updateIntervalTextField = findViewById<TextView>(R.id.UpdateIntervalTextField)

        dataTextView = findViewById(R.id.dataTextView)
        queue = Volley.newRequestQueue(this)
        ipAddress = ipAddressTextField.text.toString()
        username = usernameTextField.text.toString()
        password = passwordTextField.text.toString()
        interval = updateIntervalTextField.text.toString().toLong()
        mainHandler = Handler(Looper.getMainLooper())
        dataTextView.movementMethod = ScrollingMovementMethod()


        val connectButton = findViewById<Button>(R.id.ConnectButton)
        connectButton?.setOnClickListener {
            mainHandler.post(object : Runnable {
                override fun run() {
                    connect()
                    mainHandler.postDelayed(this, 1000)
                }
            })
        }
    }

    private fun connect() {

        val stringRequest = object: StringRequest(Request.Method.GET, "http://192.168.87.128:82/mahm",
                { response -> dataTextView.text = stringifyReturn(MSIParser.parseMSIData(response, "UTF-8"))}, // TODO: Make encoding more dynamic.
                { error -> dataTextView.text = "That didn't work! - ${error.toString()} -\n" })
        {
            override fun getHeaders() : MutableMap<String,String> {
                val headers = HashMap<String, String>()
                val credentials = String.format("%s:%s", "MSIAfterburner", "17cc95b4017d496f82") // TODO: FORCE THIS TO USE APPLICATION SETTINGS FOR RETRIEVING USER/PASS
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