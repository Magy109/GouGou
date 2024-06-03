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

class Ruta10 : Fragment(), OnMapReadyCallback,
    GoogleMap.OnMyLocationButtonClickListener,
    GoogleMap.OnMyLocationClickListener {

    private var currentMarker: Marker? = null

    private lateinit var map: GoogleMap

    companion object {
        const val REQUEST_CODE_LOCATION = 0

        fun newInstance(): Ruta10 {
            return Ruta10()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_ruta10, container, false)
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
                LatLng(18.46263848733024,-97.38263443423587),
                LatLng(18.46219489218241,-97.3727484994567),
                LatLng(18.46181174769717,-97.37173162964325),
                LatLng(18.461156189203493,-97.37103016101601),
                LatLng(18.460377469599337,-97.3705572243278),
                LatLng(18.459601333509312,-97.37027849317195),
                LatLng( 18.459128376219,-97.3703074294881),
                LatLng( 18.457553748556236, -97.37063120240738),
                LatLng(18.452603599212807,-97.37088051627882),
                LatLng(18.45072087307328,-97.37073607744577),
                LatLng(18.44805215720052,-97.37024832527008),
                LatLng(18.447197231293586,-97.36989925229696),
                LatLng(18.44568339533369,-97.3691322154769),
                LatLng(18.444634091135526,-97.36880657115199),
                LatLng(18.442878503043673, -97.36754646776944),
                LatLng(18.441924228195646,-97.36672935016712),
                LatLng( 18.44178926295571, -97.36660466717596),
                LatLng( 18.440725163860265, -97.36631819381621),
                LatLng(18.440714412883594, -97.36793567997434),
                LatLng(18.438676470858482,-97.36858318543659),
                LatLng(18.439042825071425,-97.36986278259823),
                LatLng( 18.44030921188108, -97.36961937650356),
                LatLng( 18.439950088649677,-97.37068032718209),
                LatLng(18.439383451746934,-97.3708095944506),
                LatLng( 18.439512365374917,-97.37113329999636),
                LatLng( 18.439897363589797, -97.37101844467205),
                LatLng(18.439947390678043,-97.37070603377758),
                LatLng(18.44028947133456,-97.36961586977525),
                LatLng( 18.437297864999774,-97.37021895487673),
                LatLng(18.435666632669623, -97.37058709372778),
                LatLng(18.4363615045407,-97.37367709261858),
                LatLng(18.438271287706428, -97.37387003867512),
                LatLng(18.43982235216349,-97.37410127118278),
                LatLng(18.440048201246597,-97.37420708129129),
                LatLng(18.4403019509261,-97.37442617452561),
                LatLng(18.44047594149329,-97.37474410312942),
                LatLng(18.440891471442683,-97.37672545078958),
                LatLng(18.441558443774483,-97.38554872986516),
                LatLng(18.441234583646292, -97.38675452529938),
                LatLng(18.439287430038405,-97.39269562417944),
                LatLng(18.441582257258162,-97.38554553846043),
                LatLng(18.441554649609245,-97.38491277238226),
                LatLng(18.441762807415913,-97.38490206866673),
                LatLng(18.44168725710233, -97.38387670590082),
                LatLng(18.442831111940563,-97.38375697470138),
                LatLng(18.44298595815772, -97.38355092918984),
                LatLng(18.443619557190843,-97.38287083486195),
                LatLng(18.44355609515702,-97.3828173162862),
                LatLng(18.441379214302728, -97.38303470790743),
                LatLng( 18.44125089380016,-97.38116958342546),
                LatLng( 18.441630637650945, -97.38026623818634),
                LatLng(18.444107722290312,-97.3811910107797),
                LatLng( 18.44526937451458, -97.38152590730729),
                LatLng( 18.445742034877796,-97.38179446900867),
                LatLng(18.447195300394625, -97.38258170561858),
                LatLng( 18.44643826804561,-97.38418319005824),
                LatLng(18.446294387117135,-97.38455757497942),
                LatLng(18.445749418189138,-97.38534751337919),
                LatLng(18.444265232567574,-97.38723672329024),
                LatLng(18.444704481287673,-97.38741456927029),
                LatLng(18.44473494286339,-97.38753699301328),
                LatLng(18.44285178399899,-97.38782678918894),
                LatLng( 18.44236655744757, -97.38934756046564),
                LatLng(18.44335602025366,-97.38918633262824),
                LatLng(18.4433915590363,-97.38953955522935),
                LatLng( 18.445281382675077,-97.38924655465338),
                LatLng(18.44471095897852,-97.38741246171618),
                LatLng(18.444259944348957,  -97.38724099466593),
                LatLng(18.446308689823837,-97.38455721907958),
                LatLng( 18.44731757251587,-97.38536238206794),
                LatLng( 18.44963962551978, -97.38718390812635),
                LatLng( 18.45066329403518,-97.38805114894377),
                LatLng( 18.451861625033487,-97.38884881810787),
                LatLng(18.452145921012374,-97.38919133699409),
                LatLng(18.452239840116036,-97.38936794829493),
                LatLng(18.452665619022028,-97.38976399986318),
                LatLng( 18.45445064227779, -97.39114117534338),
                LatLng(18.45441720013534,-97.3895480169628),
                LatLng(18.45673387059672, -97.38950504192405),
                LatLng(18.457312017377546,-97.38948982219756),
                LatLng(18.459564944306123, -97.3894590106318),
                LatLng(18.464872753175527,-97.38936863946553),
                LatLng(18.464818105855556, -97.38835674038663),
                LatLng(18.464644587129342,-97.38343601850225),
                LatLng( 18.464633845426903,-97.38258490191564),
                LatLng( 18.462681431343583,-97.38265550541416))
        // .color(Color.BLUE)
            .clickable(true);
        val polyline = map.addPolyline(polylineOptions)
        polyline.tag = "Ruta 10"
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
