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

class Ruta17 : Fragment(), OnMapReadyCallback,
    GoogleMap.OnMyLocationButtonClickListener,
    GoogleMap.OnMyLocationClickListener {

    private var currentMarker: Marker? = null

    private lateinit var map: GoogleMap

    companion object {
        const val REQUEST_CODE_LOCATION = 0

        fun newInstance(): Ruta17 {
            return Ruta17()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_ruta17, container, false)
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
               LatLng(18.47958239195431, -97.44292750633136),
        LatLng(18.47934815178033, -97.44302310950353),
         LatLng(18.47754344781825, -97.4425881931073),
         LatLng(18.47723969962871, -97.44277667885689),
         LatLng(18.47627926275912, -97.44141566539486),
         LatLng(18.47498517650277, -97.43942255881093),
         LatLng(18.47453179306372, -97.43876927221808),
         LatLng(18.472868342511845, -97.43586101057534),
         LatLng(18.47234549798864, -97.43407503409404),
        LatLng(18.471831658728476, -97.43178055796733),
       LatLng(18.470516831090094, -97.42437337354814),
         LatLng(18.47050620760473, -97.42383690704729),
         LatLng(18.470714491954993, -97.42352361492053),
         LatLng(18.468440619177755, -97.42040827309688),
         LatLng(18.46808963810264, -97.41978273837161),
         LatLng(18.46645668897999, -97.41538947576699),
         LatLng(18.465431866459554, -97.41250321589871),
        LatLng(18.4639283394459, -97.40839388494236),
         LatLng(18.463511317823702, -97.40714596247413),
         LatLng(18.463421543933947, -97.40658028872282),
         LatLng(18.4627218921286, -97.38487533975375),
         LatLng(18.457310906027274, -97.38522131467121),
         LatLng(18.4575123628194, -97.3915401761256),
        LatLng(18.458725748862264, -97.39153336558833),
         LatLng(18.464890359622558, -97.39141221770093),
         LatLng(18.465239213635925, -97.40402756414441),
        LatLng(18.463489603187085, -97.4040910102624),
         LatLng(18.46344987319557, -97.40409418580936),
         LatLng(18.463541297328717, -97.40640855124917),
        LatLng(18.463651279800033, -97.40724601302315),
         LatLng(18.468248324553798, -97.4199126945031),


         LatLng(18.46874157686355, -97.4206730480063),
        LatLng(18.473462287394383, -97.4270323445572),
        LatLng(18.473738061198432, -97.42741894737661),
        LatLng(18.474030288274264, -97.42766554664965),
         LatLng(18.474426394967438, -97.4278115469093),
        LatLng(18.474497243222203, -97.42818843130057),
         LatLng(18.47423783039008, -97.42906968519053),
         LatLng(18.47422869832313, -97.42954152582136),
         LatLng(18.47479472216125, -97.43197936888545),
        LatLng(18.475933844992895, -97.43650602145098),
         LatLng(18.479563289284258, -97.44293524271804))
        // .color(Color.BLUE)
            .clickable(true);
        val polyline = map.addPolyline(polylineOptions)
        polyline.tag = "Ruta 28"
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
