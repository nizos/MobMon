package com.example.mobmon.ui.widgets

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mobmon.R
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
        var listViewOfWidgets = root.findViewById<ListView>(R.id.widget_list)
        listViewOfWidgets.adapter = ArrayAdapter<String>(requireContext(), R.layout.listrow, keyStringArray)
        //listViewOfWidgets.adapter = CustomAdapter(requireContext())
        //TODO: metricsData.value can be null
        metricsData.observe(viewLifecycleOwner, Observer {
            keyStringArray.clear()
            metricsData.value?.forEach { keyStringArray.add(it.key) }
            Log.i("Widgets", "${keyStringArray}")
            //listViewOfWidgets.adapter = adapter
        });

        listViewOfWidgets.setOnItemClickListener { parent, view, position, id ->
            Toast.makeText(requireContext(),keyStringArray[position],Toast.LENGTH_SHORT).show()
        }

        return root
    }

    private class CustomAdapter(context: Context): BaseAdapter() {
        private val mContext: Context

        init {
            mContext = context
        }

        override fun getCount(): Int {
            return 5
        }

        override fun getItem(position: Int): Any {
            return position
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val textView = TextView(mContext)
            textView.text = "Here is my row"
            return textView
        }
    }
}
