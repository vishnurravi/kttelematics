//package com.myktapp
//
//import android.content.pm.PackageManager
//import android.location.Location
//import android.location.LocationRequest
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.widget.Toast
//import androidx.core.app.ActivityCompat
//import com.google.android.gms.location.FusedLocationProviderClient
//import com.google.android.gms.location.LocationCallback
//import com.google.android.gms.maps.CameraUpdateFactory
//import com.google.android.gms.maps.GoogleMap
//import com.google.android.gms.maps.OnMapReadyCallback
//import com.google.android.gms.maps.model.LatLng
//import com.google.android.gms.maps.model.MarkerOptions
//import com.myktapp.databinding.ActivityLocationBinding
//
//class LocationActivity : AppCompatActivity() , OnMapReadyCallback {
//
//    private lateinit var binding: ActivityLocationBinding
//    private lateinit var googleMap: GoogleMap
//    private lateinit var fusedLocationClient: FusedLocationProviderClient
//    private lateinit var locationCallback: LocationCallback
//    private val locationPermissionCode = 2
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityLocationBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        // Request location permission if not granted
//        if (ActivityCompat.checkSelfPermission(
//                this,
//                android.Manifest.permission.ACCESS_FINE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            ActivityCompat.requestPermissions(
//                this,
//                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
//                locationPermissionCode
//            )
//        } else {
//            initMap()
//        }
//
//        // Schedule periodic location update task
//        val locationUpdateRequest = PeriodicWorkRequest.Builder(
//            LocationUpdateWorker::class.java,
//            15, TimeUnit.MINUTES
//        ).build()
//
//        WorkManager.getInstance(this).enqueue(locationUpdateRequest)
//    }
//
//    private fun initMap() {
//        val mapFragment = supportFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment
//        mapFragment.getMapAsync(this)
//    }
//
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        if (requestCode == locationPermissionCode) {
//            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                initMap()
//            } else {
//                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }
//
//
//            // Request location updates
//            val locationRequest = LocationRequest.create()
//                .setInterval(10000) // 10 seconds interval
//                .setFastestInterval(5000) // 5 seconds fastest interval
//                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
//
//            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)
//        }
//
//
//    private fun updateLocationOnMap(location: Location) {
//        val latLng = LatLng(location.latitude, location.longitude)
//        googleMap.clear() // Clear previous markers
//        googleMap.addMarker(MarkerOptions().position(latLng).title("Current Location"))
//        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
//    }
//
//    override fun onMapReady(p0: GoogleMap) {
//        map?.let {
//            googleMap = it
//            googleMap.isMyLocationEnabled = true
//
//            // Initialize FusedLocationProviderClient
//            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
//
//            // Initialize location callback
//            locationCallback = object : LocationCallback() {
//                override fun onLocationResult(locationResult: LocationResult?) {
//                    locationResult?.lastLocation?.let { location ->
//                        updateLocationOnMap(location)
//                    }
//                }    }
//}