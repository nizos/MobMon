package com.example.mobmon.ui.settings

import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.text.InputFilter
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.Observer
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.mobmon.MainActivity
import com.example.mobmon.R
import com.example.mobmon.controller.MainController
import com.example.mobmon.controller.WidgetController
import com.example.mobmon.data.MSIParser

class SettingsActivity : MainActivity() {
    private val tag = "mobmon"

    private lateinit var ipTextField1: TextView
    private lateinit var ipTextField2: TextView
    private lateinit var ipTextField3: TextView
    private lateinit var ipTextField4: TextView
    private lateinit var portTextField: TextView
    private lateinit var usernameTextField: TextView
    private lateinit var passwordTextField: TextView
    private lateinit var updateIntervalTextField: TextView

    private lateinit var sharedPreferences: SharedPreferences
    private val _ERROR = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val rootView: View = layoutInflater.inflate(R.layout.activity_settings, frameLayout)
        Log.i(tag, "Settings Activity")

        //TODO PROBABLY MOVE THE MASTER KEY/SHARED PREFERENCES TO THE CONTROLLER
        val masterKey = MasterKey.Builder(baseContext, "mobmon_connections_")
        // TODO Add profile name in here
        val masterKeyAlias = masterKey.setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build()

        sharedPreferences = EncryptedSharedPreferences.create(
            baseContext,
            // TODO Add profile name in here
            "mobmon_connections_",
            masterKeyAlias,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

        val root = rootView
        val statusText = root.findViewById<TextView>(R.id.connectionStatusText)
        MainController.status.observe(this, Observer {
            newStatus -> if (newStatus) {
                statusText.text = "Connected"
                statusText.setTextColor(Color.parseColor("#009688"))
            } else {
                statusText.text = "Disconnected"
                statusText.setTextColor(Color.parseColor("#985EFF"))
            }
        })

        // Set up all text field bindings.
        ipTextField1 = root.findViewById(R.id.ipAddressTextField1)
        ipTextField2 = root.findViewById(R.id.ipAddressTextField2)
        ipTextField3 = root.findViewById(R.id.ipAddressTextField3)
        ipTextField4 = root.findViewById(R.id.ipAddressTextField4)
        portTextField = root.findViewById(R.id.portTextField)
        usernameTextField = root.findViewById(R.id.usernameTextField)
        passwordTextField = root.findViewById(R.id.passwordTextField)
        updateIntervalTextField = root.findViewById(R.id.updateIntervalTextField)

        // Apply IP fields size restrictions
        ipTextField1.filters = arrayOf(InputFilter.LengthFilter(3))
        ipTextField2.filters = arrayOf(InputFilter.LengthFilter(3))
        ipTextField3.filters = arrayOf(InputFilter.LengthFilter(3))
        ipTextField4.filters = arrayOf(InputFilter.LengthFilter(3))
        portTextField.filters = arrayOf(InputFilter.LengthFilter(5)) // Max 65535

        // Set up saved values in the IP fields
        ipTextField1.text = sharedPreferences.getInt("IPSegment1", 127).toString()
        ipTextField2.text = sharedPreferences.getInt("IPSegment2", 0).toString()
        ipTextField3.text = sharedPreferences.getInt("IPSegment3", 0).toString()
        ipTextField4.text = sharedPreferences.getInt("IPSegment4", 1).toString()
        portTextField.text = sharedPreferences.getInt("PortNumber", 43673).toString()

        usernameTextField.text = sharedPreferences.getString("Username", "MSIAfterburner")
        passwordTextField.text = sharedPreferences.getString("Password", "17cc95b4017d496f82")

        updateIntervalTextField.text = sharedPreferences.getInt("Update Interval", 1000).toString()

        val connectBtn: Button = root.findViewById<Button>(R.id.connectButton)
        connectBtn.setOnClickListener { _ -> connect() }

    }

    private fun connect() {

        resetFieldBackgrounds()

        val ipSegment1 = verifyIPSegment(ipTextField1)
        val ipSegment2 = verifyIPSegment(ipTextField2)
        val ipSegment3 = verifyIPSegment(ipTextField3)
        val ipSegment4 = verifyIPSegment(ipTextField4)
        val portNumber = verifyPortNumber(portTextField)
        val updateFreq = verifyUpdateFrequency(updateIntervalTextField)

        var errored = false
        if(ipSegment1 < 0 || ipSegment2 < 0 || ipSegment3 < 0 || ipSegment4 < 0
                || portNumber < 0 || updateFreq < 0)
            errored = true



        if (usernameTextField.text.toString().isBlank()) {
            usernameTextField.setBackgroundResource(R.drawable.errorborder)
            errored = true
        }

        if (passwordTextField.text.toString().isBlank()) {
            passwordTextField.setBackgroundResource(R.drawable.errorborder)
            errored = true
        }

        if (errored)
            return
        else {

            sharedPreferences.edit()
                    .putInt("IPSegment1", ipSegment1)
                    .putInt("IPSegment2", ipSegment2)
                    .putInt("IPSegment3", ipSegment3)
                    .putInt("IPSegment4", ipSegment4)
                    .putInt("PortNumber", portNumber)
                    .putString("Username", usernameTextField.text.toString())
                    .putString("Password", passwordTextField.text.toString())
                    .putInt("Update Interval", updateFreq)
                    .apply()
            var address: String = "http://%d.%d.%d.%d:%d/mahm".format(ipSegment1, ipSegment2, ipSegment3, ipSegment4, portNumber)
            tryConnectOnce(address, usernameTextField.text.toString(), passwordTextField.text.toString(), updateFreq.toString())

            // TODO SIGNAL CONNECTION HANDLER THAT THERE ARE NEW DETAILS
        }

    }

    private fun verifyIPSegment(texter: TextView) : Int {

        var toReturn = verifyTextFieldInt(texter)

        if (toReturn > 256) {
            texter.setBackgroundResource(R.drawable.errorborder)
            return _ERROR
        }

        return toReturn
    }

    private fun verifyPortNumber(porter: TextView) : Int {

        val toReturn = verifyTextFieldInt(porter)

        if (toReturn > 65535) {
            porter.setBackgroundResource(R.drawable.errorborder)
            return _ERROR
        }

        return toReturn
    }

    private fun verifyUpdateFrequency(frequer: TextView) : Int {

        val toReturn = verifyTextFieldInt(frequer)

        if (toReturn < 500 || toReturn > 10000) { // TODO maybe make this configurable, but half a second to 10 seconds should be enough?
            frequer.setBackgroundResource(R.drawable.errorborder)
            return _ERROR
        }

        return toReturn
    }

    private fun verifyTextFieldInt(inter: TextView) : Int {

        val txt = inter.text.toString()

        // Reads like return txt.toInt if not empty,
        // else return _Error. Looks weird, but was recommended by Kotlin.
        return if (txt.isNotEmpty())
            txt.toInt()
        else {
            inter.setBackgroundResource(R.drawable.errorborder)
            _ERROR
        }
    }

    private fun resetFieldBackgrounds() {
        ipTextField1.setBackgroundResource(0)
        ipTextField2.setBackgroundResource(0)
        ipTextField3.setBackgroundResource(0)
        ipTextField4.setBackgroundResource(0)
        portTextField.setBackgroundResource(0)
        usernameTextField.setBackgroundResource(0)
        passwordTextField.setBackgroundResource(0)
        updateIntervalTextField.setBackgroundResource(0)
    }

    private fun tryConnectOnce(ip: String, user: String, pass: String, interval: String) {
        Log.i("mobmon/connect", "Attempting one connection....")
        var stringRequest = object: StringRequest(
                Method.GET, ip,
                {
                    Log.i("mobmon/connect", "Successfully connected!")
                    MainController.connect(ip, user, pass, interval)
                }, // TODO: Make encoding more dynamic.
                { error -> notifyConnectionError(error.toString())})
        {
            override fun getHeaders() : MutableMap<String,String> {
                val headers = HashMap<String, String>()
                val credentials = String.format("%s:%s", user, pass) // TODO: FORCE THIS TO USE APPLICATION SETTINGS FOR RETRIEVING USER/PASS
                val auth = Base64.encodeToString(credentials.toByteArray(), Base64.NO_WRAP)
                headers["Authorization"] = "Basic $auth"
                return headers
            }
        }

        MainController.queue.add(stringRequest)
    }

    fun notifyConnectionError(error: String) {
        Log.e("mobmon/connection", "Running error notification")

        val builder = NotificationCompat.Builder(this, "MobMon Connection")
                .setSmallIcon(R.drawable.icon_about)
                .setContentTitle("Could not connect!")
                .setContentText(error)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(this)) {
            notify(235235, builder.build())
        }
    }
}

