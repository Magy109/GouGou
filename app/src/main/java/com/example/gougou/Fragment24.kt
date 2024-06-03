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

class Fragment24 : Fragment(), OnMapReadyCallback,
    GoogleMap.OnMyLocationButtonClickListener,
    GoogleMap.OnMyLocationClickListener {

    private var currentMarker: Marker? = null

    private lateinit var map: GoogleMap

    companion object {
        const val REQUEST_CODE_LOCATION = 0

        fun newInstance(): Fragment24 {
            return Fragment24()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_24, container, false)
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
                LatLng(18.45884514015482, -97.39420676642142),
                LatLng(18.45838141127132, -97.38281014218154),
                LatLng(18.46062955084507, -97.3826954594797),
                LatLng(18.460671517690372, -97.38392543044228),
                LatLng(18.46266303869004, -97.3832078368164),
                LatLng(18.462658842055205, -97.3826238218274),
                LatLng(18.4646447975431, -97.38257630812755),
                LatLng(18.464911501770146, -97.39303397217346),
                LatLng(18.47786953570605, -97.39264413999238),
                LatLng(18.480963066034363, -97.39209929923196),
                LatLng(18.48327318761136, -97.39168165657439),
                LatLng(18.48343152750914, -97.39277688041574),
                LatLng(18.48303574916652, -97.39284050868358),
                LatLng(18.483210387960384, -97.39386613509201),
                LatLng(18.481829287900013, -97.3941010298209),
                LatLng(18.482069861643026, -97.39549933403626),
                LatLng(18.481227413647403, -97.39565386541226),
                LatLng(18.481489955743626, -97.39736692094428),
                LatLng(18.48300420041238, -97.3978252513513),
                LatLng(18.48326139573689, -97.39785538288368),
                LatLng(18.48707301667369, -97.39903017263585),
                LatLng(18.48873831063881, -97.3997679105164),
                LatLng(18.489811787238537, -97.40010911803338),
                LatLng(18.49012073834345, -97.40022885320607),
                LatLng(18.490349346702857, -97.39998780105815),
                LatLng(18.490708134571335, -97.39990410235747),
                LatLng(18.49071130968224, -97.39935838682646),
                LatLng(18.489629878912467, -97.39888433863588),
                LatLng(18.48940746000629, -97.39716887083237),
                LatLng(18.491705385900516, -97.39695522369777),
                LatLng(18.491437970385732, -97.39546932461077),
                LatLng(18.491287537997934, -97.39401244944166),
                LatLng(18.493635326555946, -97.39358421993245),
                LatLng(18.493231512400612, -97.39088484196577),
                LatLng(18.493907799750573, -97.39083797056983),
                LatLng(18.493770415844168, -97.38701366578775),
                LatLng(18.493846617119118, -97.38700696989177),
                LatLng(18.493986320144572, -97.39091170479074),
                LatLng(18.493830742633648, -97.39088826915436),
                LatLng(18.490429403220077, -97.39074197826628),
                LatLng(18.48892281220455, -97.39107044607488),
                LatLng(18.488994978803902, -97.39153314426123),
                LatLng(18.48871239090576, -97.39159005937798),
                LatLng(18.489179061471944, -97.39526896327948),
                LatLng(18.48694519943365, -97.39552813215512),
                LatLng(18.48699535472342, -97.39597370040259),
                LatLng(18.4857614440633, -97.39621916124533),
                LatLng(18.48629805242146, -97.39887469078994),
                LatLng(18.48320944967338, -97.39794076153558),
                LatLng(18.48299988307801, -97.39780684361415),
                LatLng(18.481461974247907, -97.39737159852304),
                LatLng(18.481204359201158, -97.39564802650108),
                LatLng(18.482063300012612, -97.39550044923027),
                LatLng(18.48180927811505, -97.39410100718135),
                LatLng(18.4832254461582, -97.39387783968034),
                LatLng(18.48303598374096, -97.39288114584824),
                LatLng(18.483448956523475, -97.39280069492703),
                LatLng(18.483274317845044, -97.39167243627894),
                LatLng(18.477955502849866, -97.3926292417113),
                LatLng(18.47700874397343, -97.39269474418174),
                LatLng(18.477173862840104, -97.39372591217816),
                LatLng(18.475260657749445, -97.39379032833389),
                LatLng(18.47496640030309, -97.39583491963613),
                LatLng(18.470314197899654, -97.39391047040223),
                LatLng(18.46914832518803, -97.39390728590378),
                LatLng(18.469227437633833, -97.39783830263318),
                LatLng(18.463168561284505, -97.39811570245566),
                LatLng(18.4630110533341, -97.39404794028971),
                LatLng(18.45884514015482, -97.39420676642142))
            // .color(Color.BLUE)
            .clickable(true);
        val polyline = map.addPolyline(polylineOptions)
        polyline.tag = "Ruta 24"
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
