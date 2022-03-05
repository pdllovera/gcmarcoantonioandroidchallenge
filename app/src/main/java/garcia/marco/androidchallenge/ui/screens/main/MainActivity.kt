package garcia.marco.androidchallenge.ui.screens.main

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.*
import garcia.marco.androidchallenge.R
import garcia.marco.androidchallenge.databinding.ActivityMainBinding
import garcia.marco.androidchallenge.ui.Constants
import garcia.marco.androidchallenge.ui.screens.BaseActivity
import java.text.NumberFormat

class MainActivity : BaseActivity(), OnMapReadyCallback {

    private lateinit var binding: ActivityMainBinding

    private lateinit var map: GoogleMap
    private var latitude: Double = 0.toDouble()
    private var longitude: Double = 0.toDouble()

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var lastLocation: Location

    // 1
    private lateinit var locationCallback: LocationCallback
    // 2
    private lateinit var locationRequest: LocationRequest
    private var locationUpdateState = false

    private var isTracking = false
    //private var pathPoints = mutableListOf<Polyline>()

    //private var map: GoogleMap? = null

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
        private const val REQUEST_CHECK_SETTINGS = 2
    }

    override fun createView() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        getLastLocation()

        binding.mapView.getMapAsync {
            Log.d("MainActivity", "getMapAsync: ${it}")
            map = it
        }

    }

    override fun collectFlows() {

    }

    private fun initMap() {
        binding.mapView.onCreate(Bundle())
        binding.mapView.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        if(googleMap != null) {
            map = googleMap

            val mex = LatLng(latitude, longitude)
            map.animateCamera(CameraUpdateFactory.newCameraPosition(CameraPosition.builder().target((mex)).zoom(16f).build()))

            binding.mapView.onResume()

            map.uiSettings.isMyLocationButtonEnabled = false
            map.uiSettings.isZoomControlsEnabled = true

            addMarker(latitude, longitude)
        }
    }

    private fun getLastLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        fusedLocationClient?.lastLocation!!.addOnCompleteListener(this) { task ->
            if (task.isSuccessful && task.result != null) {
                lastLocation = task.result!!
                Log.d("MainActivity", "lastLocation: ${lastLocation}")
                latitude = lastLocation.latitude
                longitude = lastLocation.longitude
                initMap()
            }
            else {
                Log.d("MainActivity", "getLastLocation:exception", task.exception)
                //showMessage("No location detected. Make sure location is enabled on the device.")
            }
        }
    }

    private fun addMarker(latitud: Double, longitud: Double) {
        val format = NumberFormat.getInstance()
        var loc : Location? = null
        val locations = LatLng(latitud, longitud)
        val markerOptions = MarkerOptions()
            .position(locations)
            .icon(BitmapDescriptorFactory.fromResource(R.drawable.location_pin))
        map.addMarker(markerOptions)
    }

}