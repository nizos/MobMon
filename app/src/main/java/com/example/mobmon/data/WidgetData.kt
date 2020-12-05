package com.example.mobmon.data

import android.util.Base64
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import com.example.mobmon.R


public class WidgetData(activity: FragmentActivity?) {
    private var ipAddress: String = "http://192.168.1.154:82/mahm"
    private var username: String = "MSIAfterburner"
    private var password: String = "17cc95b4017d496f82"
    lateinit var dataTextView: TextView
    var dataResponse: String? = null
    //lateinit var mainHandler: Handler
    lateinit var queue: RequestQueue
    private var context = activity

    //Initiliaze requestqueue with sent context
    init {
        queue = Volley.newRequestQueue(context)
    }

    fun update() {
        connect(ipAddress,username,password)
    }

    fun onMSIResponse(response : String) {
        testResponse = stringifyReturn(MSIParser.parseMSIData(response, "UTF-8"));
        //TODO: Add event listener for reading new information?
    }

    fun connect(ip: String, user: String, pass: String) {
        val dashBoardText = context?.findViewById<TextView>(R.id.text_dashboard)
        val stringRequest = object: StringRequest(
                Request.Method.GET, ip, { response -> this.onMSIResponse(response) },
                { error -> dashBoardText?.text = "That didn't work! - ${error.toString()} -\n" })
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