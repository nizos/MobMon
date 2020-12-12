package com.example.mobmon.ui.support
//
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.TextView
//import androidx.fragment.app.Fragment
//import androidx.lifecycle.Observer
//import androidx.lifecycle.ViewModelProvider
//import com.example.mobmon.R
//
//class SupportFragment : Fragment() {
//
//    private lateinit var supportViewModel: SupportViewModel
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        supportViewModel =
//            ViewModelProvider(this).get(SupportViewModel::class.java)
//        val root = inflater.inflate(R.layout.fragment_support, container, false)
//        val textView: TextView = root.findViewById(R.id.text_support)
//        supportViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })
//        return root
//    }
//}
