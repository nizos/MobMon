package com.example.mobmon.ui.support

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import com.example.mobmon.MainActivity
import com.example.mobmon.R


class SupportActivity : MainActivity() {
    private val tag = "mobmon"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val rootView: View = layoutInflater.inflate(R.layout.activity_support, frameLayout)
        Log.i(tag, "Support Activity")

        val installBtn = rootView.findViewById<Button>(R.id.installationGuideButton)
        installBtn.setOnClickListener {
            val uri: Uri = Uri.parse("https://github.com/nizos/MobMon/wiki/Connection-Components-Installation-Guide")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }
    }
}
