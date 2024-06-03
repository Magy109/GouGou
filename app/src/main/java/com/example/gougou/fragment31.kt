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

class fragment31: Fragment(), OnMapReadyCallback,
    GoogleMap.OnMyLocationButtonClickListener,
    GoogleMap.OnMyLocationClickListener {

    private var currentMarker: Marker? = null

    private lateinit var map: GoogleMap

    companion object {
        const val REQUEST_CODE_LOCATION = 0

        fun newInstance(): fragment31{
            return fragment31()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_fragment31, container, false)
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
                LatLng(18.444712488566637, -97.41401741131756),
                LatLng(18.44515783273836, -97.41286656212131),
                LatLng(18.444777672177807, -97.40907714530067),
                LatLng(18.442924184161996, -97.40895665364819),
                LatLng(18.44266092546887, -97.40544047833956),
                LatLng(18.44387870247337, -97.40466249064599),
                LatLng(18.443245054265304, -97.40386443018369),
                LatLng(18.442730995622398, -97.40307037248547),
                LatLng(18.442085355168643, -97.40285079419823),
                LatLng(18.44131329388452, -97.40267836592231),
                LatLng(18.440747307731115, -97.40242986943366),
                LatLng(18.440836378499554, -97.40218843279477),
                LatLng(18.441251193186474, -97.40234134266568),
                LatLng(18.442722680728664, -97.40277599297313),
                LatLng(18.444377469941173, -97.40478390861418),
                LatLng(18.4440888718035, -97.40165836347315),
                LatLng(18.445982293249983, -97.40178042653598),
                LatLng(18.446129089466126, -97.40341105351068),
                LatLng(18.44864670628928, -97.40237450306834),
                LatLng(18.44889328073134, -97.40383838366863),
                LatLng(18.450114138959435, -97.40391344028176),
                LatLng(18.44983201385503, -97.3983743190959),
                LatLng(18.45622700345662, -97.3981552729647),
                LatLng(18.456225842962453, -97.39666433542699),
                LatLng(18.458881518141325, -97.39659318546953),
                LatLng(18.458811109456406, -97.39456107289993),
                LatLng(18.45876664522845, -97.3924524995946),
                LatLng(18.458744562397115, -97.39204080100572),
                LatLng(18.458541540324177, -97.38607611707315),
                LatLng(18.46075691382478, -97.38604911555021),
                LatLng(18.46078512405984, -97.38711790440118),
                LatLng(18.46478369268462, -97.38702209074214),
                LatLng(18.464867372034917, -97.38934524706708),
                LatLng(18.460840893011834, -97.38944682907801),
                LatLng(18.460850187992605, -97.39148574598906),
                LatLng(18.460933661844592, -97.39412254970512),
                LatLng(18.456684462581777, -97.3942989937699),
                LatLng(18.456888251663415, -97.39815872062131),
                LatLng(18.449903413259037, -97.39839019860862),
                LatLng(18.450154469549602, -97.40395678294972),
                LatLng(18.44796801818829, -97.40379994397763),
                LatLng(18.44785643472892, -97.40281970924124),
                LatLng(18.44611550795662, -97.40343671053536),
                LatLng(18.44595520887661, -97.40180835075422),
                LatLng(18.44319782430378, -97.40159562230271),
                LatLng(18.443353334948938, -97.40358262965528),
                LatLng(18.4444093671391, -97.40481492639765),
                LatLng(18.444285791092966, -97.40502645009111),
                LatLng(18.444429920582053, -97.40516006682479),
                LatLng(18.44450107715569, -97.40619957262534),
                LatLng(18.444614250720257, -97.40738179792805),
                LatLng(18.44518327047355, -97.41286831721834),
                LatLng(18.444712488566637, -97.41401741131756))
        // .color(Color.GREEN)
            .clickable(true);
        val polyline = map.addPolyline(polylineOptions)
        polyline.tag = "Ruta 31"
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
