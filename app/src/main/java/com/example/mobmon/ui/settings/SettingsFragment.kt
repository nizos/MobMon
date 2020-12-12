package com.example.mobmon.ui.settings
//
//import android.content.SharedPreferences
//import android.os.Bundle
//import android.text.InputFilter
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Button
//import android.widget.TextView
//import androidx.fragment.app.Fragment
//import androidx.lifecycle.Observer
//import androidx.lifecycle.ViewModelProvider
//import androidx.security.crypto.EncryptedSharedPreferences
//import androidx.security.crypto.MasterKey
//import com.example.mobmon.R
//import com.example.mobmon.controller.MainController
//
//class SettingsFragment : Fragment() {
//
//    private lateinit var settingsViewModel: SettingsViewModel
//    private lateinit var root: View
//
//    // Connection settings fields.

//
//    // Object for storing preferences encoded

//
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View?
//    {
//        settingsViewModel =
//            ViewModelProvider(this).get(SettingsViewModel::class.java)
//        root = inflater.inflate(R.layout.fragment_settings, container, false)
//        textView = root.findViewById(R.id.dataTextView)
//        settingsViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//

//}
