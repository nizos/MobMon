package com.example.mobmon

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import com.example.mobmon.controller.MainController
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
        setContentView(R.layout.activity_main)
        context = this
        appContext = this
        initView()
        frameLayout = findViewById<View>(R.id.container) as FrameLayout
        drawer = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        if (!hasStarted) {
            intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
            finish()
            hasStarted = true
        }


        MainController.profileName.observe(this, Observer {
            newProfileName -> activeProfile.text = newProfileName
        })

        MainController.status.observe(this, Observer {
            newStatus -> if (newStatus) {
                connectionStatus.text = "Connected"
                connectionStatus.setTextColor(Color.parseColor("#009688"))
            } else {
                connectionStatus.text = "Disconnected"
                connectionStatus.setTextColor(Color.parseColor("#985EFF"))
            }
        })
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
                intent = Intent(this, DashboardActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.nav_profiles -> {
                intent = Intent(this, ProfilesActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.nav_widgets -> {
                intent = Intent(this, WidgetsActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.nav_settings -> {
                intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.nav_support -> {
                intent = Intent(this, SupportActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.nav_about -> {
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
