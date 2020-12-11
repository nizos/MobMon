package com.example.mobmon

import android.os.Bundle
import android.view.Menu
import android.content.Context
import android.util.Log
import android.view.View
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import com.example.mobmon.Widgets.*
import kotlinx.android.synthetic.main.fragment_dashboard.*

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private val TAG = "mobmon"

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        super.onCreate(savedInstanceState)





        Log.i("mobmon", "STARTED!")


        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        MainActivity.appContext = applicationContext
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.nav_dashboard, R.id.nav_widgets, R.id.nav_profiles, R.id.nav_settings, R.id.nav_support, R.id.nav_about), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        var test: MutableMap<String, MutableMap<String, String>> = mutableMapOf<String,MutableMap<String,String>>()
        test.put("First Key", mutableMapOf(Pair("Second key","Value")))
        var widgetList = mutableListOf<Widget>()
        widgetList.add(Line("Line"))


        setContentView(R.layout.fragment_dashboard)
        parentCoordinatorLayout.addDraggableChild(draggableCard1)
        parentCoordinatorLayout.addDraggableChild(draggableCard2)
        parentCoordinatorLayout.addDraggableChild(draggableCard3)
        parentCoordinatorLayout.addDraggableChild(draggableCard4)

        parentCoordinatorLayout.setViewDragListener(object :
            DraggableCoordinatorLayout.ViewDragListener {
            override fun onViewCaptured(view: View, i: Int) {

                when (view.id) {
                    R.id.draggableCard1 -> draggableCard1.isDragged = true
                    R.id.draggableCard2 -> draggableCard2.isDragged = true
                    R.id.draggableCard3 -> draggableCard3.isDragged = true
                    R.id.draggableCard4 -> draggableCard4.isDragged = true
                }
            }

            override fun onViewReleased(view: View, v: Float, v1: Float) {

                when (view.id) {
                    R.id.draggableCard1 -> draggableCard1.isDragged = false
                    R.id.draggableCard2 -> draggableCard2.isDragged = false
                    R.id.draggableCard3 -> draggableCard3.isDragged = false
                    R.id.draggableCard4 -> draggableCard4.isDragged = false
                }
            }
        })

        draggableCard1.setOnLongClickListener {
            draggableCard1.isChecked = !draggableCard1.isChecked
            Log.i(TAG, "draggableCard1 Clicked!")
            true
        }

        draggableCard2.setOnLongClickListener {
            draggableCard2.isChecked = !draggableCard2.isChecked
            Log.i(TAG, "draggableCard2 Clicked!")
            true
        }

        draggableCard3.setOnLongClickListener {
            draggableCard3.isChecked = !draggableCard3.isChecked
            Log.i(TAG, "draggableCard3 Clicked!")
            true
        }

        draggableCard4.setOnLongClickListener {
            draggableCard4.isChecked = !draggableCard4.isChecked
            Log.i(TAG, "draggableCard4 Clicked!")
            true
        }



    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    companion object {
        lateinit var appContext: Context
    }
}
