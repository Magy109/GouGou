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

class Ruta8 : Fragment(), OnMapReadyCallback,
    GoogleMap.OnMyLocationButtonClickListener,
    GoogleMap.OnMyLocationClickListener {

    private var currentMarker: Marker? = null

    private lateinit var map: GoogleMap

    companion object {
        const val REQUEST_CODE_LOCATION = 0

        fun newInstance(): Ruta8{
            return Ruta8()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_ruta8, container, false)
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
                LatLng(18.45400904015817, -97.42907481870674),
                        LatLng(18.456184648513513, -97.42825966081425),
        LatLng(18.457527869854474, -97.4289397728009),
        LatLng(18.457807821103884, -97.42835155005558),
        LatLng(18.464338701629615, -97.43172323809354),
        LatLng(18.46444356965243, -97.43156261510919),
        LatLng(18.46481354257672, -97.43058057009056),
        LatLng(18.465078866891247, -97.43037197504951),
        LatLng(18.465214818352138, -97.43010006841794),
        LatLng(18.465144843349265, -97.4297965447364),
        LatLng(18.464211994410945, -97.42927207198096),
        LatLng(18.46368430282537, -97.42904084678698),
        LatLng(18.463494785337318, -97.42889078291623),
        LatLng(18.462446535439028, -97.42841958534547),
        LatLng(18.462261943339797, -97.4283003856307),
        LatLng(18.461209690464784, -97.42755372916692),
        LatLng(18.461133715829277, -97.42741882975268),
        LatLng(18.460499369892958, -97.42694357905187),
        LatLng(18.460375410747815, -97.42689931518203),
        LatLng(18.46023945545305, -97.42678760160449),
        LatLng(18.460227459392115, -97.42663794756731),
        LatLng(18.460367413632767, -97.42609766903378),
        LatLng(18.46136822435402, -97.42619949336445),
        LatLng(18.461806897318183, -97.4237202859048),
        LatLng(18.46022836405386, -97.42284620937198),
        LatLng(18.46060572390094, -97.41899930417873),
        LatLng(18.46018185120306, -97.4086008158565),
        LatLng(18.459760795696504, -97.40863364379618),
        LatLng(18.45952820952968, -97.40878874428621),
        LatLng(18.45696558925202, -97.4034676576245),
        LatLng(18.456988401002576, -97.40238736476105),
        LatLng(18.45626203447334, -97.40239477078585),
        LatLng(18.456234927637965, -97.39884251931112),
        LatLng(18.457051745994647, -97.39880137523117),
        LatLng(18.45892449469106, -97.39901048423145),
        LatLng(18.458653410096844, -97.38947523728484),
        LatLng(18.45832599256447, -97.38174905403018),
        LatLng(18.457912271346686, -97.37947552663577),
        LatLng(18.45681571094937, -97.37965865962956),
        LatLng(18.456719451151727, -97.37897199965246),
        LatLng(18.452804352147055, -97.37959912864342),
        LatLng(18.455861406167557, -97.37910811405955),
        LatLng(18.45591938368645, -97.37982389865813),
        LatLng(18.457906226064722, -97.37950341591907),
        LatLng(18.459915449349424, -97.3790734282225),
        LatLng(18.460626039423474, -97.38269156102773),
        LatLng(18.4606953902573, -97.38445062279379),
        LatLng(18.46081627996911, -97.38943346896271),
        LatLng(18.453375819074736, -97.38960184266968),
        LatLng(18.453386731507415, -97.39017004880895),
        LatLng(18.456524714138453, -97.39254870645716),
        LatLng(18.457045878401914, -97.40237382580212),
        LatLng(18.457112746464887, -97.40257120959308),
        LatLng(18.457103825542845, -97.40314051619721),
        LatLng(18.46024739976737, -97.40971942091966),
        LatLng(18.46063306878783, -97.4189621178804),
        LatLng(18.460263506756775, -97.42284262808856),
        LatLng(18.46087016449097, -97.42324599505838),
        LatLng(18.46181520964602, -97.42370655723688),
        LatLng(18.46139607777583, -97.42622257065122),
        LatLng(18.460359345874807, -97.42611561960209),
        LatLng(18.46023007004075, -97.42662787753521),
        LatLng(18.4602657323491, -97.4267970636416),
        LatLng(18.460426212646965, -97.42689105592277),
        LatLng(18.46113527168167, -97.42741471803919),
        LatLng(18.46124036660045, -97.42757284767207),
        LatLng(18.462479684009807, -97.4284167251719),
        LatLng(18.463484254467474, -97.4288707179132),
        LatLng(18.463813855648354, -97.42908910835946),
        LatLng(18.46465956243466, -97.42956718664766),
        LatLng(18.465172701947225, -97.42980951030388),
        LatLng(18.465199447908816, -97.43010088637604),
        LatLng(18.46508354871078, -97.43039696206219),
        LatLng(18.464838377070635, -97.43058494662455),
        LatLng(18.46432389174214, -97.4317504717652),
        LatLng(18.458767622508773, -97.42886752731452),
        LatLng(18.458486114113654, -97.42945883264191),
        LatLng(18.456180372502686, -97.42825255521002),
        LatLng(18.456141984442908, -97.42889557164987),
        LatLng(18.454203342987554, -97.42958146147686),
        LatLng(18.45400904015817, -97.42907481870674))
        // .color(Color.BLUE)
            .clickable(true);
        val polyline = map.addPolyline(polylineOptions)
        polyline.tag = "Ruta 8"
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
