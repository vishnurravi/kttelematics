package com.myktapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth
import com.myktapp.databinding.ActivityMainBinding
import com.myktapp.databinding.ActivityMapBinding
import com.myktapp.dataclsforinfo.LocationModel
import com.myktapp.realclasss.LocationMigration
import io.realm.Realm
import io.realm.RealmConfiguration
import java.util.Locale
import java.util.Timer
import java.util.TimerTask

class MapActivity : AppCompatActivity() ,LocationListener {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityMapBinding
    private lateinit var locationManager: LocationManager
    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private val permissionId = 2
    private lateinit var realm: Realm

    private lateinit var tvGpsLocation: TextView
    private val locationPermissionCode = 2
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMapBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        auth = FirebaseAuth.getInstance()

        val email = intent.getStringExtra("email")
        val diplnm = intent.getStringExtra("dname")
//        binding.dispalyName.setText(diplnm)
//        binding.dispalyEmail.setText(email)

        binding.signout.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this, SignUp::class.java))
        }

        val button: Button = findViewById(R.id.getLocation)
        button.setOnClickListener {
            getLocation()
        }
        Realm.init(this)
        realm = Realm.getDefaultInstance()


        startLocation()
    }

    private fun startLocation() {
        val timer = Timer()
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                val location = getLastKno()
                location?.let {
                    saveLocationToRealm(location.latitude, location.longitude)
                }
            }
        }, 0, 15 * 60 * 1000)
    }

    private fun getLastKno(): Location? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }


    private fun getLocation() {
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if ((ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED)
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                locationPermissionCode
            )
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5f, this)
    }

    override fun onLocationChanged(location: Location) {
        tvGpsLocation = findViewById(R.id.tv_latitude)
        tvGpsLocation.text =
            "Latitude: " + location.latitude + " , Longitude: " + location.longitude
        saveLocationToRealm(location.latitude, location.longitude)
    }


    override fun onProviderDisabled(provider: String) {
        Toast.makeText(this, "Location provider $provider disabled", Toast.LENGTH_SHORT).show()
        val settingsIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        startActivity(settingsIntent)    }

    private fun saveLocationToRealm(latitude: Double, longitude: Double) {
        realm.executeTransactionAsync { realm ->
            val locationModel = LocationModel()
            locationModel.latitude = latitude
            locationModel.longitude = longitude

            Log.i("TAG", "saveLocationToRealm: ${ locationModel.longitude}")
//            realm.insert(locationModel)
        }
        }




    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    )  {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == locationPermissionCode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }
}