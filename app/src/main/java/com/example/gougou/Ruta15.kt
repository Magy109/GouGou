package com.example.gougou
import android.Manifest
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.*
import android.graphics.Color
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.model.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions

class Ruta15 : Fragment(), OnMapReadyCallback,
    GoogleMap.OnMyLocationButtonClickListener,
    GoogleMap.OnMyLocationClickListener {

    private var currentMarker: Marker? = null

    private lateinit var map: GoogleMap

    companion object {
        const val REQUEST_CODE_LOCATION = 0

        fun newInstance(): Ruta15 {
            return Ruta15()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_ruta15, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createMapFragment()
    }

    private fun createMapFragment() {
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        createMarker()
        createPolylines()
        map.setOnMyLocationButtonClickListener(this)
        map.setOnMyLocationClickListener(this)
        map.setOnPolylineClickListener { polyline ->
            val polylineTag = polyline.tag as String
            Toast.makeText(requireContext(), polylineTag, Toast.LENGTH_SHORT).show()
        }
        enableLocation()

    }

    private fun createMarker() {
        val favoritePlace = LatLng(18.462323487879104, -97.39250785951711)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(favoritePlace,15f))
    }

    private fun isLocationPermissionsGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun enableLocation() {
        if (!::map.isInitialized) return
        if (isLocationPermissionsGranted()) {
            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            map.isMyLocationEnabled = true

            // Iniciar actualizaciones de ubicación
            val locationRequest = LocationRequest.create().apply {
                priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                interval = 1000 // Intervalo de actualización de ubicación en milisegundos
            }
            val locationCallback = object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult?) {
                    locationResult?.lastLocation?.let { location ->
                        updateMarkerPosition(location)
                    }
                }
            }
            LocationServices.getFusedLocationProviderClient(requireContext())
                .requestLocationUpdates(locationRequest, locationCallback, null)
        } else {
            requestLocationPermission()
        }
    }


    private fun requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        ) {
            Toast.makeText(
                requireContext(),
                "Ve a ajustes y acepta los permisos",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_CODE_LOCATION
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CODE_LOCATION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    map.isMyLocationEnabled = true
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Para activar la localización ve a ajustes y acepta los permisos",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun onMyLocationButtonClick(): Boolean {
        return false
    }

    override fun onMyLocationClick(location: Location) {
        Toast.makeText(requireContext(), "Mi ubicacion", Toast.LENGTH_SHORT).show()
    }


    private fun createPolylines() {
        val polylineOptions = PolylineOptions()
            .add(
                LatLng(18.457382495911958, -97.3872312081891),
                LatLng(18.46078145672662, -97.38710923796212),
                LatLng(18.460855187964512, -97.39147818497551),
                LatLng(18.464888421764698, -97.39141436708384),
                LatLng(18.465001740204656, -97.39644025706683),
                LatLng(18.46918977797648, -97.39638520226683),
                LatLng(18.469208489700435, -97.39989086930326),
                LatLng(18.469873438954806, -97.39988130577211),
                LatLng(18.47230096375796, -97.39937892394164),
                LatLng(18.472291403124046, -97.39955532368874),
                LatLng(18.472574021423384, -97.400468782317),
                LatLng(18.47278300720629, -97.40133526781726),
                LatLng(18.474437719795887, -97.40110282513444),
                LatLng(18.474718558066257, -97.40140882179246),
                LatLng(18.474999103171612, -97.40360541785476),
                LatLng(18.473174199550982, -97.40369994072802),
                LatLng(18.4733265770721, -97.40460799396124),
                LatLng(18.475122366756736, -97.40439432370805),
                LatLng(18.475263672734286, -97.4051666604656),
                LatLng(18.476689973995065, -97.40499355949883),
                LatLng(18.476103729052113, -97.4004991060808),
                LatLng(18.47751596974163, -97.40035379361441),
                LatLng(18.47800206238429, -97.40342335562323),
                LatLng(18.47799297117507, -97.40456214069661),
                LatLng(18.478164715604795, -97.40499291022762),
                LatLng(18.478453826718983, -97.40613669539178),
                LatLng(18.478713482488118, -97.40788803082246),
                LatLng(18.481476598449476, -97.40765591803685),
                LatLng(18.48078440335307, -97.40453150726702),
                LatLng(18.480199733839328, -97.40159838923705),
                LatLng(18.47982283548619, -97.39986614284307),
                LatLng(18.477527047554318, -97.40035764670543),
                LatLng(18.47609813727884, -97.40049544852458),
                LatLng(18.47671535239148, -97.40498733956814),
                LatLng(18.47527687569449, -97.40520233369924),
                LatLng(18.475141081275282, -97.40439535603696),
                LatLng(18.473350205380484, -97.40463932929353),
                LatLng(18.473146511422556, -97.40368267029095),
                LatLng(18.474985922703127, -97.403585053783),
                LatLng(18.47470198935305, -97.40144396522543),
                LatLng(18.4744427444991, -97.40115111042903),
                LatLng(18.47278576574034, -97.40135697512315),
                LatLng(18.47229196450961, -97.39954779697267),
                LatLng(18.472322827400376, -97.39935256044149),
                LatLng(18.472347515648224, -97.39769955438027),
                LatLng(18.46724257630808, -97.39791819983387),
                LatLng(18.467048606578757, -97.39397999371359),
                LatLng(18.46299832126003, -97.39405898805401),
                LatLng(18.462873012990116, -97.38939776364633),
                LatLng(18.45867634346604, -97.38947634461195),
                LatLng(18.45849115433562, -97.38513559123022),
                LatLng(18.457323462303677, -97.38522019013719),
                LatLng(18.457391366577696, -97.3872376342918))
        // .color(Color.BLUE)
            .clickable(true);
        val polyline = map.addPolyline(polylineOptions)
        polyline.tag = "Ruta 15(frailes)"
    }
    private fun updateMarkerPosition(location: Location) {
        val currentLatLng = LatLng(location.latitude, location.longitude)
        val markerIcon = BitmapDescriptorFactory.fromResource(R.drawable.p)
        if (currentMarker == null) {
            currentMarker = map.addMarker(MarkerOptions().position(currentLatLng).icon(markerIcon))
        } else {
            // Si hay un marcador existente, actualiza su posición
            currentMarker?.position = currentLatLng
        }
    }
}
