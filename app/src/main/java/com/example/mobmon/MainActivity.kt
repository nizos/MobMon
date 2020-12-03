package com.example.mobmon

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.method.ScrollingMovementMethod
import android.util.Base64
import android.view.Menu
import android.widget.Button
import android.widget.TextView
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.mobmon.data.MSIParser

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
//    private var ipAddress: String = "http://192.168.87.128:82/mahm"
//    private var username: String = "MSIAfterburner"
//    private var password: String = "17cc95b4017d496f82"
//    private var interval: Long = 1000
//    lateinit var mainHandler: Handler
//    lateinit var dataTextView: TextView
//    lateinit var queue: RequestQueue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf(
                R.id.nav_dashboard, R.id.nav_widgets, R.id.nav_profiles, R.id.nav_settings, R.id.nav_support, R.id.nav_about), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

//        val ipAddressTextField = findViewById<TextView>(R.id.IpAddressTextField)
//        val usernameTextField = findViewById<TextView>(R.id.UsernameTextField)
//        val passwordTextField = findViewById<TextView>(R.id.PasswordTextField)
//        val updateIntervalTextField = findViewById<TextView>(R.id.UpdateIntervalTextField)
//
//        dataTextView = findViewById(R.id.dataTextView)
//        queue = Volley.newRequestQueue(this)
//        mainHandler = Handler(Looper.getMainLooper())
//        dataTextView.movementMethod = ScrollingMovementMethod()
//
//
//        val connectButton = findViewById<Button>(R.id.ConnectButton)
//        connectButton?.setOnClickListener {
//            ipAddress = ipAddressTextField.text.toString()
//            username = usernameTextField.text.toString()
//            password = passwordTextField.text.toString()
//            interval = updateIntervalTextField.text.toString().toLong()
//            mainHandler.post(object : Runnable {
//                override fun run() {
//                    connect(ipAddress, username, password)
//                    mainHandler.postDelayed(this, 1000)
//                }
//            })
//        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

//    private fun connect(ip: String, user: String, pass: String) {
//
//        val stringRequest = object: StringRequest(
//            Request.Method.GET, ip,
//            { response -> dataTextView.text = stringifyReturn(MSIParser.parseMSIData(response, "UTF-8"))}, // TODO: Make encoding more dynamic.
//            { error -> dataTextView.text = "That didn't work! - ${error.toString()} -\n" })
//        {
//            override fun getHeaders() : MutableMap<String,String> {
//                val headers = HashMap<String, String>()
//                val credentials = String.format("%s:%s", user, pass) // TODO: FORCE THIS TO USE APPLICATION SETTINGS FOR RETRIEVING USER/PASS
//                val auth = Base64.encodeToString(credentials.toByteArray(), Base64.NO_WRAP)
//                headers["Authorization"] = "Basic $auth"
//                return headers
//            }
//        }
//        // Add the request to the RequestQueue.
//        queue.add(stringRequest)
//    }
//
//    fun stringifyReturn(input: MutableMap<String, MutableMap<String,String>>): String {
//        var returnString = ""
//        input.forEach {
//            returnString += it.key + "\n"
//            it.value.forEach {
//                returnString += "-> " + it.key + " = " + it.value + "\n"
//            }
//        }
//        return returnString
//    }
}