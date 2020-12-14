package com.example.mobmon.fragments.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mobmon.R
import com.example.mobmon.data.Widget
import com.example.mobmon.data.WidgetViewModel
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.view.*

class AddFragment : Fragment() {
    private lateinit var widgetViewModel: WidgetViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add, container, false)
        widgetViewModel = ViewModelProvider(this).get(WidgetViewModel::class.java)
        view.newWidgetButton.setOnClickListener {
            insertDataToDatabase()
        }
        return view;
    }

    private fun insertDataToDatabase() {
        val widgetId = newWidgetId.text.toString().toInt()
        val widgetName = newWidgetName.text.toString()
        val widgetType = newWidgetType.text.toString()
        val widgetMetrics = newWidgetMetrics.text.toString()
        val widgetColor = newWidgetColor.text.toString()
        val widgetPositionX = newWidgetPositionX.text.toString().toFloat()
        val widgetPositionY = newWidgetPositionY.text.toString().toFloat()

        val widget = Widget(uid = 0, widgetId = widgetId, widgetName = widgetName, widgetType = widgetType, widgetMetrics = widgetMetrics, widgetColor = widgetColor, widgetPositionX = widgetPositionX, widgetPositionY = widgetPositionY)
        widgetViewModel.addWidget(widget = widget)
        Toast.makeText(requireContext(), "Successfully added new widget", Toast.LENGTH_LONG).show()
        findNavController().navigate(R.id.action_addFragment_to_listFragment)
    }
}