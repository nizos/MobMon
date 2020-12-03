package com.example.mobmon.ui.widgets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mobmon.R

class WidgetsFragment : Fragment() {

    private lateinit var widgetsViewModel: WidgetsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        widgetsViewModel =
            ViewModelProvider(this).get(WidgetsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_widgets, container, false)
        val textView: TextView = root.findViewById(R.id.text_widgets)
        widgetsViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}
