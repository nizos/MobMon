package com.example.mobmon.ui.widget

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mobmon.R
import com.example.mobmon.ui.widget.WidgetViewModel

class WidgetFragment : Fragment() {

    private lateinit var widgetViewModel: WidgetViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        widgetViewModel =
                ViewModelProvider(this).get(WidgetViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_widget, container, false)

        val textView: TextView = root.findViewById(R.id.text_widget)
        widgetViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })



        return root
    }
}