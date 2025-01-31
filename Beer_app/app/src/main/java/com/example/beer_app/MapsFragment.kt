package com.example.beer_app
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MapsFragment : Fragment() {

    private val callback = OnMapReadyCallback { googleMap ->
        val poland = LatLng(51.9194, 19.1451)  // Coordinates of Poland
        googleMap.addMarker(MarkerOptions().position(poland).title("Marker in Poland"))

        val zoomLevel = 6f
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(poland, zoomLevel))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

        val filtrButton = requireActivity().findViewById<FloatingActionButton>(R.id.filtr_button)
        filtrButton.setOnClickListener {
            ShowFilter(view)
        }
    }

    fun ShowFilter(view: View) {
        val navigationViewFiltr = view.findViewById<BottomNavigationView>(R.id.nav_filtr_bottom)

        val screenHeight = resources.displayMetrics.heightPixels
        val targetTranslationY = (screenHeight * 0.2).toFloat()

        if (navigationViewFiltr.translationY <= targetTranslationY) {
            navigationViewFiltr.animate().translationY(navigationViewFiltr.height.toFloat()).setDuration(300).start()
        } else {
            navigationViewFiltr.animate().translationY(targetTranslationY).setDuration(300).start()
        }
    }
}
