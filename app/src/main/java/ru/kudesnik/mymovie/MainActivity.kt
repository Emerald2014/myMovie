package ru.kudesnik.mymovie

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import kotlinx.coroutines.*
import ru.kudesnik.mymovie.ui.content_provider.ContentProviderFragment
//import ru.kudesnik.mymovie.ui.details.DetailsViewModel
import ru.kudesnik.mymovie.ui.favourite.FavouriteFragment
import ru.kudesnik.mymovie.ui.history.HistoryFragment
import ru.kudesnik.mymovie.ui.main.MainFragment
import ru.kudesnik.mymovie.ui.maps.MapsFragment

private const val REFRESH_PERIOD = 1000L
private const val MINIMAL_DISTANCE = 1f

class MainActivity : AppCompatActivity(), CoroutineScope by MainScope() {

    private val permissionResult =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                getLocation()
            } else {
                Toast.makeText(
                    this, getString(R.string.dialog_message_no_gps),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    private val onLocationListener =
        LocationListener { p0 -> getAddressAsync(this@MainActivity, p0) }

    //А это тоже самое без лямбды
/*    private val onLocationListener = object : LocationListener {
        override fun onLocationChanged(p0: Location) {
            getAddressAsync(this@MainActivity, p0)
        }
    }*/

/*    override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
    override fun onProviderEnabled(provider: String) {}
    override fun onProviderDisabled(provider: String) {}*/


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_favourites -> {
                openFragment(FavouriteFragment.newInstance())
                true
            }
            R.id.menu_history -> {
                openFragment(HistoryFragment.newInstance())
                true
            }
            R.id.menu_content_provider -> {
                openFragment(ContentProviderFragment.newInstance())
                true
            }
            R.id.my_position -> {
                checkLocationPermission()
                true
            }
            R.id.google_maps -> {
                openFragment(MapsFragment.newInstance())
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun openFragment(fragment: Fragment) {
        supportFragmentManager.apply {
            beginTransaction()
                .add(R.id.container, fragment)
                .addToBackStack("")
                .commitAllowingStateLoss()
        }
    }


    private fun checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) ==
            PackageManager.PERMISSION_GRANTED
        ) {
            getLocation()
        } else {

            permissionResult.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
        }

    }

    @SuppressLint("MissingPermission")
    private fun getLocation() {
        val locationManager = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            val provider = locationManager.getProvider(LocationManager.GPS_PROVIDER)
            provider?.let {
                //Будем получать геоположение через каждые 60 секунд или каждые 100 метров
                locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    REFRESH_PERIOD,
                    MINIMAL_DISTANCE,
                    onLocationListener
                )
            }
        } else {
            val location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            if (location == null) {
                Toast.makeText(
                    this,
                    getString(R.string.dialog_title_gps_turned_off),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                getAddressAsync(this, location)
            }
        }
    }

    private fun getAddressAsync(context: Context, location: Location) {
        val geoCoder = Geocoder(context)
        launch {
            val job = async(Dispatchers.IO) {
                geoCoder.getFromLocation(location.latitude, location.longitude, 1)
            }
            val addresses = job.await()
            showAddressDialog(addresses[0].getAddressLine(0), location)
        }
    }

    private fun showAddressDialog(address: String, location: Location) {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.dialog_address_title))
            .setMessage(address)
            .setPositiveButton(getString(R.string.dialog_address_get_weather)) { _, _ ->
                Toast.makeText(this, "Открываем город $address", Toast.LENGTH_SHORT).show()
//                openDetailsCityFragment(
//                    City(                        address, location.latitude, location.longitude)
//                )
            }
            .setNegativeButton(getString(R.string.dialog_button_close)) { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

}