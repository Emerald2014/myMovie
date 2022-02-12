package ru.kudesnik.mymovie.ui.maps

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Address
import android.location.Geocoder
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.*
import androidx.core.app.ActivityCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.kudesnik.mymovie.R
import ru.kudesnik.mymovie.databinding.FragmentMapsBinding
import ru.kudesnik.mymovie.model.AppState
import ru.kudesnik.mymovie.model.AppState.*
import ru.kudesnik.mymovie.model.entities.Movie
import ru.kudesnik.mymovie.ui.details.DetailsFragment
import ru.kudesnik.mymovie.ui.details.DetailsViewModel
import java.io.IOException
import java.util.jar.Manifest

class MapsFragment : Fragment(), CoroutineScope by MainScope() {

    private lateinit var map: GoogleMap
    private var menu: Menu? = null
    private val markers: ArrayList<Marker> = ArrayList()
    private var _binding: FragmentMapsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MapsViewModel by viewModel()

    @SuppressLint("MissingPermission")
    private val callback = OnMapReadyCallback { googleMap ->
        map = googleMap

        map.uiSettings.isZoomControlsEnabled = true
        map.uiSettings.isMyLocationButtonEnabled = true

        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            map.isMyLocationEnabled = true
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMapsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

        arguments?.getInt(BUNDLE_EXTRA_MAPS)?.let {
            viewModel.loadPerson(it)
            with(binding) {
                viewModel.movieLiveData.observe(viewLifecycleOwner) { appState ->
                    when (appState) {
                        is AppState.SuccessPers -> {
                            searchAddress.setText(appState.persData[0].birthPlace)
                            initSearchByAddress()
                        }
                        else -> {}
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.map_menu, menu)
        this.menu = menu
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_map_mode_normal -> {
                map.mapType = GoogleMap.MAP_TYPE_NORMAL
                return true
            }
            R.id.menu_map_mode_satellite -> {
                map.mapType = GoogleMap.MAP_TYPE_SATELLITE
                return true
            }
            R.id.menu_map_mode_terrain -> {
                map.mapType = GoogleMap.MAP_TYPE_TERRAIN
                return true
            }
            R.id.menu_map_traffic -> {
                map.isTrafficEnabled = !map.isTrafficEnabled
                return true
            }
        }

        return false
    }

    private fun initSearchByAddress() = with(binding) {
        buttonSearch.setOnClickListener {
            val geoCoder = Geocoder(it.context)
            val searchText = searchAddress.text.toString()
            launch(Dispatchers.IO) {
                try {
                    val addresses = geoCoder.getFromLocationName(searchText, 1)
                    if (addresses.isNotEmpty()) {
                        goToAddress(addresses, searchText)
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun goToAddress(addresses: MutableList<Address>, searchText: String) {
        launch {
            val location = LatLng(addresses[0].latitude, addresses[0].longitude)
            setMarker(location, searchText)
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15f))
        }
    }

    private fun drawLine() {
        val last: Int = markers.size - 1
        if (last >= 1) {
            val previous: LatLng = markers[last - 1].position
            val current: LatLng = markers[last].position
            map.addPolyline(
                PolylineOptions()
                    .add(previous, current)
                    .color(Color.RED)
                    .width(5f)
            )
        }
    }

    private fun setMarker(location: LatLng, searchText: String) {
        map.addMarker(
            MarkerOptions()
                .position(location)
                .title(searchText)
        )?.let { markers.add(it) }
    }

    companion object {
        fun newInstance() = MapsFragment()
        const val BUNDLE_EXTRA_MAPS = "movie"
        fun newInstance(bundle: Bundle): MapsFragment {
            val fragment = MapsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}