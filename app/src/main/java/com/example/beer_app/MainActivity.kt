package com.example.beer_app
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_login -> {
                    navController.navigate(R.id.action_mapsFragment_to_loginFragment)
                    true
                }
                else -> false
            }
        }
    }

    fun ShowTable(view: View) {
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        val navigationView_background = findViewById<FrameLayout>(R.id.nav_view_background)

        if (navigationView.translationX == 0f) {
            navigationView.animate().translationX(-navigationView.width.toFloat()).setDuration(300).start()
            navigationView_background.animate().alpha(0f).setDuration(300).start()
            navigationView_background.isVisible = view.isGone
        }
        else {
            navigationView.animate().translationX(0f).setDuration(300).start()
            navigationView_background.animate().alpha(1f).setDuration(300).start()
            navigationView_background.isVisible = true
        }
    }


}
