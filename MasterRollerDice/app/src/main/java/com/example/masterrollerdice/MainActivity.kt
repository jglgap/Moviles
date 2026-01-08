package com.example.masterrollerdice

import android.graphics.Color
import android.os.Bundle
import android.widget.Switch

import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.viewModels
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import kotlin.getValue

class MainActivity : AppCompatActivity() {


    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    val model: viewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Toolbar y título personalizado
        val toolbar: MaterialToolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false) // Desactivar título automático

     //   val toolbarTitle: TextView = findViewById(R.id.toolbarTitle)
      //  toolbarTitle.text = getString(R.string.app_name)
      //  val typeface = ResourcesCompat.getFont(this, R.font.imperial_script)
       // toolbarTitle.typeface = typeface

        // Drawer y NavigationView
        drawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)

        // NavController
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        // Destinos top-level
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.rollFragment, R.id.historicFragment, R.id.settingsFragment),
            drawerLayout
        )

        // Vincular toolbar con NavController
        setupActionBarWithNavController(navController, appBarConfiguration)

        // Vincular NavigationView con NavController
        navView.setupWithNavController(navController)

        // 🔹 Acción de la hamburguesa con color blanco
        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.open_drawer,
            R.string.close_drawer
        )
        toggle.drawerArrowDrawable.color = Color.WHITE
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()


        model.nightMode.observe(this) { isNightMode ->
            if (isNightMode) {
                findViewById<DrawerLayout>(R.id.drawer_layout).setBackgroundResource(R.color.dark_blue)
                val color = ContextCompat.getColor(this, R.color.white)
                findViewById<TextView>(R.id.toolbarTitle).setTextColor(color)

            } else {
                findViewById<DrawerLayout>(R.id.drawer_layout).setBackgroundResource(R.color.lavender)
                val color = ContextCompat.getColor(this, R.color.dark_blue)
                findViewById<TextView>(R.id.toolbarTitle).setTextColor(color)


            }
        }




    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}