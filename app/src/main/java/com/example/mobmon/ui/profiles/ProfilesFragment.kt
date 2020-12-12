package com.example.mobmon.ui.profiles
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
//class ProfilesFragment : Fragment() {
//
//    private lateinit var profilesViewModel: ProfilesViewModel
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        profilesViewModel =
//            ViewModelProvider(this).get(ProfilesViewModel::class.java)
//        val root = inflater.inflate(R.layout.fragment_profiles, container, false)
//        val textView: TextView = root.findViewById(R.id.text_profiles)
//        profilesViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })
//        return root
//    }
//}
