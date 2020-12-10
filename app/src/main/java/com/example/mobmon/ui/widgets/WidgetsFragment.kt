package com.example.mobmon.ui.widgets

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.mobmon.R
import com.example.mobmon.controller.MainController
import com.example.mobmon.controller.MainController.metricsData

class WidgetsFragment : Fragment() {

    private lateinit var widgetsViewModel: WidgetsViewModel
    var keyStringArray: MutableList<String> = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        widgetsViewModel =
                ViewModelProvider(this).get(WidgetsViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_widgets, container, false)
        //TODO: metricsData.value can be null
        metricsData.observe(viewLifecycleOwner, Observer {
            metricsData.value?.forEach { keyStringArray.add(it.key) }
            Log.i("Widgets","${keyStringArray}")
        });

        return root
    }
}
