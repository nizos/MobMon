package com.example.mobmon.ui.settings

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mobmon.R
import com.example.mobmon.controller.MainController

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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        settingsViewModel =
            ViewModelProvider(this).get(SettingsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_settings, container, false)
        val textView: TextView = root.findViewById(R.id.text_settings)
        settingsViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
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
}
