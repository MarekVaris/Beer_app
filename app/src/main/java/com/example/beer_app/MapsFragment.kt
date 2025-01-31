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
import com.google.android.material.floatingactionbutton.FloatingActionButton
import android.util.Log
import java.io.InputStream
import java.nio.charset.Charset
import org.json.JSONObject

class MapsFragment : Fragment() {
    private lateinit var mMap: GoogleMap

    private val callback = OnMapReadyCallback { googleMap ->
        mMap = googleMap

        val poland = LatLng(51.9194, 19.1451)
        val zoomLevel = 6f
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(poland, zoomLevel))

        loadGeoJsonFromAsset()
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
        // Implementacja przycisku filtrów (jeśli potrzebna)
    }

    private fun loadGeoJsonFromAsset() {
        try {
            Log.d("MapsFragment", "Sprawdzanie plików w assets...")
            val assetFiles = requireContext().assets.list("") ?: emptyArray()
            Log.d("MapsFragment", "Zawartość assets: ${assetFiles.joinToString()}")

            if (!assetFiles.contains("export.geojson")) {

                Log.e("MapsFragment", "Plik export.geojson NIE ISTNIEJE w assets!")
                return
            }
            Log.d("MapsFragment", "Wczytywanie pliku export.geojson")

            val inputStream: InputStream = requireContext().assets.open("export.geojson")
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            val json = String(buffer, Charset.forName("UTF-8"))

            Log.d("MapsFragment", "Plik wczytany poprawnie, długość: ${json.length}")

            parseGeoJson(json)
        } catch (e: Exception) {
            Log.e("MapsFragment", "Błąd wczytywania pliku: ${e.message}")
        }
    }

    private fun parseGeoJson(json: String) {
        try {
            val jsonObject = JSONObject(json)
            val features = jsonObject.getJSONArray("features")

            for (i in 0 until features.length()) {
                val feature = features.getJSONObject(i)
                val geometry = feature.getJSONObject("geometry")
                val properties = feature.getJSONObject("properties")

                if (geometry.getString("type") == "Point") {
                    val coordinates = geometry.getJSONArray("coordinates")
                    val lon = coordinates.getDouble(0)
                    val lat = coordinates.getDouble(1)
                    val name = properties.optString("name", "Pub")

                    val position = LatLng(lat, lon)
                    mMap.addMarker(MarkerOptions().position(position).title(name))
                }
            }
        } catch (e: Exception) {
            Log.e("MapsFragment", "Błąd parsowania JSON: ${e.message}")
        }
    }
}