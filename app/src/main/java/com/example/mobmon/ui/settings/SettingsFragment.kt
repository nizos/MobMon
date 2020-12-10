package com.example.mobmon.ui.settings

import android.content.SharedPreferences
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.text.InputFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.example.mobmon.R
import com.example.mobmon.controller.MainController
import org.w3c.dom.Text

class SettingsFragment : Fragment() {

    private lateinit var settingsViewModel: SettingsViewModel
    lateinit var address: String
    lateinit var username: String
    lateinit var password: String
    lateinit var interval: String
    lateinit var addressTextField: TextView
    lateinit var usernameTextField: TextView
    lateinit var passwordTextField: TextView
    lateinit var intervalTextField: TextView
    lateinit var dataTextView: TextView
    lateinit var saveButton: Button
    private lateinit var root: View

    // Connection settings fields.
    private lateinit var ipTextField1: TextView
    private lateinit var ipTextField2: TextView
    private lateinit var ipTextField3: TextView
    private lateinit var ipTextField4: TextView
    private lateinit var portTextField: TextView
    private lateinit var usernameTextField: TextView
    private lateinit var passwordTextField: TextView
    private lateinit var updateIntervalTextField: TextView

    private lateinit var textView: TextView

    // Object for storing preferences encoded
    private lateinit var sharedPreferences: SharedPreferences
    private val _ERROR = -1


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        settingsViewModel =
            ViewModelProvider(this).get(SettingsViewModel::class.java)
        root = inflater.inflate(R.layout.fragment_settings, container, false)
        textView = root.findViewById(R.id.dataTextView)
        settingsViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it

        //TODO PROBABLY MOVE THE MASTER KEY/SHARED PREFERENCES TO THE CONTROLLER
        val masterKey = MasterKey.Builder(requireContext(), "mobmon_connections_") // TODO Add profile name in here
        val masterKeyAlias = masterKey.setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build()

        sharedPreferences = EncryptedSharedPreferences.create(
            requireContext(),
            "mobmon_connections_", // TODO Add profile name in here
            masterKeyAlias,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

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
        })

        address = "http://192.168.87.128:82/mahm"
        username = "MSIAfterburner"
        password = "17cc95b4017d496f82"
        interval = "1000"

        addressTextField = root.findViewById<TextView>(R.id.textField_Address)
        usernameTextField = root.findViewById<TextView>(R.id.textField_Username)
        passwordTextField = root.findViewById<TextView>(R.id.textField_Password)
        intervalTextField = root.findViewById<TextView>(R.id.textField_Interval)

        saveButton = root.findViewById<Button>(R.id.button_Save)
        dataTextView = root.findViewById<TextView>(R.id.textView_Data)
        dataTextView.movementMethod = ScrollingMovementMethod()

        addressTextField.text = address
        usernameTextField.text = username
        passwordTextField.text = password
        intervalTextField.text = interval

        val settingsText: TextView = root.findViewById(R.id.text_settings)

        saveButton?.setOnClickListener {
            MainController.connect(address, username, password, interval)
        }

        return root
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

        textView.text = "IP = [$ipSegment1.$ipSegment2.$ipSegment3.$ipSegment4]"

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
}
