package com.example.mobmon.ui.about

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.mobmon.MainActivity
import com.example.mobmon.R

class AboutActivity : MainActivity() {
    private val tag = "mobmon"
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val rootView: View = layoutInflater.inflate(R.layout.activity_about, frameLayout)
        Log.i(tag, "About Activity")

        val aboutTextView = rootView.findViewById<TextView>(R.id.aboutTextView)
        aboutTextView.text = "Created By:\n\n" +
                "Nizar Selander - nizos @ github\n" +
                "Martin Moberg - Epicoustic @ github\n" +
                "Joel Amundberg - jberg-dev @ github\n\n" +
                "---\n\n" +
                "Created Fall 2020 for the course\n" +
                "PA1469 - Utveckling av mobila applikationer\n" +
                "at Blekinge Institute of Technology\n\n" +
                "---\n\n" +
                "The version control repository with the source\n" +
                "and acknowledgements can be found by\n" +
                "clicking the button below."

        val githubButton = rootView.findViewById<Button>(R.id.githubButton)
        githubButton.setOnClickListener {
            val uri: Uri = Uri.parse("https://github.com/nizos/MobMon")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }
    }


}
