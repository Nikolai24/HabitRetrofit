package com.example.habitretrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.habitretrofit.fragment.InfoFragment
import com.example.habitretrofit.fragment.MainFragment
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    var check = HOME

    private lateinit var drawerLayout: DrawerLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)
        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, MainFragment(), FIRST).commit()
            navigationView.setCheckedItem(R.id.menu_item_home)
        }
    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_item_home -> if (check == INFO ) {
                var fragment = supportFragmentManager.findFragmentByTag(ABOUT)
                supportFragmentManager.beginTransaction()
                    .hide(fragment!!)
                    .commit()
                fragment = supportFragmentManager.findFragmentByTag(FIRST)
                supportFragmentManager.beginTransaction()
                    .show(fragment!!)
                    .commit()
                check = HOME
            }
            R.id.menu_item_info -> {
                var fragment = supportFragmentManager.findFragmentByTag(FIRST)
                supportFragmentManager.beginTransaction()
                    .hide(fragment!!)
                    .commit()
                supportFragmentManager.beginTransaction()
                    .add(R.id.fragment_container, InfoFragment(), ABOUT)
                    .commit()
                check = INFO
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            onBackPressedDispatcher.onBackPressed()
        }
    }
    companion object {
        private const val HOME = "Home"
        private const val INFO = "Info"
        private const val FIRST = "First Fragment"
        private const val ABOUT = "About Fragment"
    }
}