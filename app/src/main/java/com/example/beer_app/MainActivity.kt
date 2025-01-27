package com.example.beer_app
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.navigation.NavigationView
import java.io.Console


class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController


    }

    fun ShowTable(view: View) {
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        val navigationView_background = findViewById<FrameLayout>(R.id.nav_view_background)

        if (navigationView.translationX == 0f) {
            navigationView.animate().translationX(-navigationView.width.toFloat()).setDuration(300).start()
            navigationView_background.animate().alpha(0f).setDuration(300).start()
            navigationView_background.isClickable = false
        }
        else {
            navigationView.animate().translationX(0f).setDuration(300).start()
            navigationView_background.animate().alpha(1f).setDuration(300).start()
            navigationView_background.isClickable = true
        }
    }


}
