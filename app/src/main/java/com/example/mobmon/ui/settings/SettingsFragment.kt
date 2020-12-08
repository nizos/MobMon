package com.example.mobmon.ui.settings

import android.content.SharedPreferences
import android.os.Bundle
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
import org.w3c.dom.Text

class SettingsFragment : Fragment() {

    private lateinit var settingsViewModel: SettingsViewModel
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

        val masterKey = MasterKey.Builder(requireContext(), "mobmon_connections_") // TODO Add profile name in here
        val masterKeyAlias = masterKey.setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build()
        //MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

        sharedPreferences = EncryptedSharedPreferences.create(
            requireContext(),
            "mobmon_connections_", // TODO Add profile name in here
            masterKeyAlias,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

        ipTextField1 = root.findViewById(R.id.ipAddressTextField1)
        ipTextField2 = root.findViewById(R.id.ipAddressTextField2)
        ipTextField3 = root.findViewById(R.id.ipAddressTextField3)
        ipTextField4 = root.findViewById(R.id.ipAddressTextField4)
        portTextField = root.findViewById(R.id.portTextField)
        usernameTextField = root.findViewById(R.id.usernameTextField)
        passwordTextField = root.findViewById(R.id.passwordTextField)
        updateIntervalTextField = root.findViewById(R.id.updateIntervalTextField)

        ipTextField1.filters = arrayOf(InputFilter.LengthFilter(3))
        ipTextField2.filters = arrayOf(InputFilter.LengthFilter(3))
        ipTextField3.filters = arrayOf(InputFilter.LengthFilter(3))
        ipTextField4.filters = arrayOf(InputFilter.LengthFilter(3))

        val connectBtn: Button = root.findViewById<Button>(R.id.connectButton)
            connectBtn.setOnClickListener { event -> connect(event) }
        })

        return root
    }

    private fun connect(connectedView: View) {

        resetIPBackgrounds()

        val ipSegment1 = verifyIPSegment(ipTextField1)
        val ipSegment2 = verifyIPSegment(ipTextField2)
        val ipSegment3 = verifyIPSegment(ipTextField3)
        val ipSegment4 = verifyIPSegment(ipTextField4)

        var errored = false
        if(ipSegment1 < 0 || ipSegment2 < 0 || ipSegment3 < 0 || ipSegment4 < 0)
            errored = true

        textView.text = "IP = [$ipSegment1.$ipSegment2.$ipSegment3.$ipSegment4]"

        if (errored)
            return
    }

    private fun verifyIPSegment(texter: TextView) : Int {
        var toReturn = 0
        val ipSeg = texter.text.toString()

        if (ipSeg.isNotEmpty())
            toReturn = ipSeg.toInt()
        else {
            texter.setBackgroundResource(R.drawable.errorborder)
            return _ERROR
        }

        if (toReturn > 256) {
            texter.setBackgroundResource(R.drawable.errorborder)
            return _ERROR
        }

        return toReturn
    }

    private fun resetIPBackgrounds() {
        ipTextField1.setBackgroundResource(0)
        ipTextField2.setBackgroundResource(0)
        ipTextField3.setBackgroundResource(0)
        ipTextField4.setBackgroundResource(0)
    }
}
