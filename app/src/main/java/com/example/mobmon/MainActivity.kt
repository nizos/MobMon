package com.example.mobmon

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.mobmon.ui.about.AboutActivity
import com.example.mobmon.ui.dashboard.DashboardActivity
import com.example.mobmon.ui.profiles.ProfilesActivity
import com.example.mobmon.ui.settings.SettingsActivity
import com.example.mobmon.ui.support.SupportActivity
import com.example.mobmon.ui.widgets.WidgetsActivity
import com.google.android.material.navigation.NavigationView

open class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val tag = "mobmon"
    protected var frameLayout: FrameLayout? = null
    private lateinit var context: Context
    private lateinit var drawer: DrawerLayout
    private lateinit var navigationView: NavigationView

    lateinit var headerView: View
    lateinit var profile: ImageView
    lateinit var menuOption: ImageView
    lateinit var activeProfile: TextView
    lateinit var connectionStatus: TextView
    lateinit var toggle: ActionBarDrawerToggle


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        Log.i(tag, "MainActivity --> onCreate")
        setContentView(R.layout.activity_main)
        context = this
        appContext = this
        initView()
        frameLayout = findViewById<View>(R.id.container) as FrameLayout
        drawer = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        if(!hasStarted)
        {
            Toast.makeText(context, "You clicked on Dashboard", Toast.LENGTH_SHORT).show()
            intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
            finish()
            hasStarted = true
        }
//        var test: MutableMap<String, MutableMap<String, String>> = mutableMapOf<String,MutableMap<String,String>>()
//        test.put("First Key", mutableMapOf(Pair("Second key","Value")))
//        var widgetList = mutableListOf<Widget>()
//        widgetList.add(Line("Line"))
    }

    private fun initView() {
        menuOption = findViewById(R.id.img_menuOption)
        menuOption.setBackgroundResource(R.drawable.icon_menu)
        drawer = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.nav_view)
        headerView = navigationView.getHeaderView(0)

        profile = headerView.findViewById(R.id.img_profile)
        activeProfile = headerView.findViewById(R.id.txt_active_profile)
        connectionStatus = headerView.findViewById(R.id.txt_connection_status)

        toggle = ActionBarDrawerToggle(this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.isDrawerIndicatorEnabled = false
        toggle.syncState()
        navigationView.setNavigationItemSelectedListener(this)
        menuOption.setOnClickListener { drawer.openDrawer(GravityCompat.START) }
    }

    override fun onBackPressed() {
        drawer = findViewById(R.id.drawer_layout)
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.itemId
        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id: Int = item.itemId
        val intent: Intent
        when (id) {
            R.id.nav_dashboard -> {
                Toast.makeText(context, "You clicked on Dashboard", Toast.LENGTH_SHORT).show()
                intent = Intent(this, DashboardActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.nav_profiles -> {
                Toast.makeText(context, "You clicked on Profiles", Toast.LENGTH_SHORT).show()
                intent = Intent(this, ProfilesActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.nav_widgets -> {
                Toast.makeText(context, "You clicked on Widgets", Toast.LENGTH_SHORT).show()
                intent = Intent(this, WidgetsActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.nav_settings -> {
                Toast.makeText(context, "You clicked on Settings", Toast.LENGTH_SHORT).show()
                intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.nav_support -> {
                Toast.makeText(context, "You clicked on Support", Toast.LENGTH_SHORT).show()
                intent = Intent(this, SupportActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.nav_about -> {
                Toast.makeText(context, "You clicked on About", Toast.LENGTH_SHORT).show()
                intent = Intent(this, AboutActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    companion object {
        lateinit var appContext: Context
        var hasStarted = false
    }
}
