package com.example.pantherxcore

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView
import android.widget.TextView
import android.content.res.ColorStateList
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var toolbar: MaterialToolbar
    private lateinit var toolbarTitle: TextView
    private lateinit var navView: NavigationView // 🔹 Mover a propiedad de clase

    val model: TrainingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Toolbar y título personalizado
        toolbar = findViewById(R.id.toolbar)
        toolbarTitle = findViewById(R.id.toolbarTitle)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        // Drawer y NavigationView
        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view) // 🔹 Guardar referencia

        // NavController
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        // Destinos top-level
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.settingsFragment, R.id.trainingListFragment, R.id.addTrainingFragment),
            drawerLayout
        )

        // Sincronizar el título del toolbar con el destino actual
        navController.addOnDestinationChangedListener { _, destination, _ ->
            // Restaurar el ícono personalizado
            updateToolbarStyle(model.selectedColorOption.value)
        }

        // Vincular NavigationView con NavController
        navView.setupWithNavController(navController)

        // Establecer el icono personalizado directamente en el toolbar
        toolbar.navigationIcon = ContextCompat.getDrawable(this, R.drawable.logo_gris)

        // Configurar el listener para abrir/cerrar el drawer
        toolbar.setNavigationOnClickListener {
            if (drawerLayout.isDrawerOpen(navView)) {
                drawerLayout.closeDrawer(navView)
            } else {
                drawerLayout.openDrawer(navView)
            }
        }

        // Observar cambios de color
        observeColorChanges()
    }

    private fun observeColorChanges() {
        lifecycleScope.launch {
            model.selectedColorOption.collect { option ->
                updateToolbarStyle(option)
                updateDrawerStyle(option) // 🔹 Actualizar también el drawer
            }
        }
        lifecycleScope.launch {
            model.isBackgroundColorChanged.collect { isChanged ->
                updateDrawerBackground(isChanged)
            }
        }
    }

    private fun updateDrawerBackground(isChanged: Boolean) {
        val backgroundColorRes = if (isChanged) {
            R.color.darker
        } else {
            R.color.whiter
        }
        drawerLayout.setBackgroundResource(backgroundColorRes)
    }

    private fun updateToolbarStyle(option: Int) {
        val logoRes = when (option) {
            1 -> R.drawable.logo_morado
            2 -> R.drawable.logo_amarillo
            3 -> R.drawable.logo_naranja
            4 -> R.drawable.logo_gris
            else -> R.drawable.logo_morado
        }

        val colorRes = when (option) {
            1 -> R.color.purpura
            2 -> R.color.amarillo
            3 -> R.color.orange
            4 -> R.color.gray
            else -> R.color.purpura
        }

        // Actualizar logo
        toolbar.navigationIcon = ContextCompat.getDrawable(this, logoRes)

        // Actualizar color del texto
        toolbarTitle.setTextColor(ContextCompat.getColor(this, colorRes))
    }

    // 🔹 Nueva función para actualizar el color de fondo del drawer
    private fun updateDrawerStyle(option: Int) {
        val colorRes = when (option) {
            1 -> R.color.purpura
            2 -> R.color.amarillo
            3 -> R.color.orange
            4 -> R.color.gray
            else -> R.color.purpura
        }

        // Aplicar color de fondo al NavigationView
        navView.setBackgroundResource(colorRes)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}