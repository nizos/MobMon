package com.example.mobmon.ui.profiles

import android.os.Bundle
import android.text.SpannableStringBuilder
import android.util.Log
import android.view.TextureView
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.mobmon.MainActivity
import com.example.mobmon.R
import com.example.mobmon.controller.MainController
import com.example.mobmon.controller.MainController.profileName

class ProfilesActivity : MainActivity() {
    private val tag = "mobmon"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val rootView: View = layoutInflater.inflate(R.layout.activity_profiles, frameLayout)
        Log.i(tag, "Profiles Activity")

        val profileNameTextField = rootView.findViewById<EditText>(R.id.profileNameTextField)
        val updateProfileButton = rootView.findViewById<Button>(R.id.updateProfileButton)

        profileNameTextField.text = SpannableStringBuilder(profileName.value)
        updateProfileButton.setOnClickListener {
            profileName.apply { value = profileNameTextField.text.toString() }
        }
    }
}
