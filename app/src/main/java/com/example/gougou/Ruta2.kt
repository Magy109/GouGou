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

class Ruta2 : Fragment(), OnMapReadyCallback,
    GoogleMap.OnMyLocationButtonClickListener,
    GoogleMap.OnMyLocationClickListener {

    private var currentMarker: Marker? = null

    private lateinit var map: GoogleMap

    companion object {
        const val REQUEST_CODE_LOCATION = 0

        fun newInstance(): Ruta2 {
            return Ruta2()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_ruta2, container, false)
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
        LatLng(18.438960550349464,-97.39977891162259),
        LatLng(18.440819824506676,-97.402187577943),
        LatLng(18.440793760054675,-97.40165638917264),
        LatLng(18.443191672994146,-97.40157396339795),
        LatLng(18.44319960144307,-97.4015875018279),
        LatLng(18.44316730995979,-97.40108825079989),
        LatLng(18.444049941642064,-97.4011620037926),
        LatLng(18.443958454307634,-97.4002202379068),
        LatLng(18.445860993461167,-97.40036774380916),
        LatLng(18.44597400694194,-97.40178606118384),
        LatLng(18.449283803208687,-97.40205270409567),
        LatLng(18.450037642193678,-97.4015473584296),
        LatLng(18.44979011471274,-97.39712328880995),
        LatLng(18.44994322357377,-97.39684083019267),
        LatLng(18.463107137402133,-97.39650482968439),
        LatLng(18.463017830764016,-97.39407030541032),
        LatLng(18.460963765273107,-97.39412410705187),
        LatLng(18.461027556496518,-97.3965586313259),
        LatLng(18.475528042824365,-97.39628843405782),
        LatLng(18.474280813958288,-97.39557556248445),
        LatLng(18.474331853126955,-97.39275098153944),
        LatLng(18.476181638940744,-97.39271062991197),
        LatLng(18.476101032841115,-97.38595228766289),
        LatLng(18.476981274253404,-97.38462069876769),
        LatLng(18.47862378057269,-97.38452488560458),
        LatLng(18.486591454826424,-97.38461769417584),
        LatLng(18.487139976600687,-97.38541126838696),
        LatLng(18.477806372469942,-97.38546507788323),
        LatLng(18.477677372515387,-97.3866385858569),
        LatLng(18.47711606341477,-97.38662513544652),
        LatLng(18.47710521825863,-97.38460490054159),
        LatLng(18.47370070818826,-97.38934418944936),
        LatLng(18.454432598991076,-97.38955835816229),
        LatLng(18.45453621690899,-97.3943516803162),
        LatLng(18.451853836598943,-97.39446955608135),
        LatLng(18.45089151016836,-97.394507054881),
        LatLng(18.45028363863318,-97.39462833572024),
        LatLng(18.438966166551054,-97.39976947514602))
           // .color(Color.GREEN)
            .clickable(true);
        val polyline = map.addPolyline(polylineOptions)
        polyline.tag = "Ruta 2"
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
