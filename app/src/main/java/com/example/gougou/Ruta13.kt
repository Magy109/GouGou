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

class Ruta13 : Fragment(), OnMapReadyCallback,
    GoogleMap.OnMyLocationButtonClickListener,
    GoogleMap.OnMyLocationClickListener {

    private var currentMarker: Marker? = null

    private lateinit var map: GoogleMap

    companion object {
        const val REQUEST_CODE_LOCATION = 0

        fun newInstance(): Ruta13 {
            return Ruta13()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_ruta13, container, false)
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
        LatLng(18.458914349860592, -97.39831828788994),
        LatLng(18.458501811782014, -97.38513946960857),
        LatLng(18.457286815876245, -97.38524124321528),
        LatLng(18.457440465233333, -97.38950084851774),
        LatLng(18.455478186009316, -97.3895379697932),
        LatLng(18.45543072922196, -97.38840256824257),
        LatLng(18.45259956652012, -97.38619719399672),
        LatLng(18.451756452260966, -97.38556846439094),
        LatLng(18.449478230120864, -97.383876150632),
        LatLng(18.44813553274124, -97.38307461096221),
        LatLng(18.445027647211447, -97.38147153162171),
        LatLng(18.44488681348318, -97.3810731754547),
        LatLng(18.445972349489836, -97.38067495854256),
        LatLng(18.44500462439538, -97.37761932801337),
        LatLng(18.443870757035185, -97.3778131551173),
        LatLng(18.443840111867445, -97.3776112518838),
        LatLng(18.444966318187923, -97.37736896800426),
        LatLng(18.444682851985817, -97.37587488407776),
        LatLng(18.445272767609254, -97.37575374213775),
        LatLng(18.445334057428155, -97.37614139634603),
        LatLng(18.44477478702163, -97.37624638602735),
        LatLng(18.445019946875604, -97.37763548027208),
        LatLng(18.44598815441755, -97.38068202796508),
        LatLng(18.44656321975917, -97.37868815499229),
        LatLng(18.4468236990855, -97.37948769128879),
        LatLng(18.448997430448486, -97.37872201850362),
        LatLng(18.45049405724177, -97.38346078013151),
        LatLng(18.44946297853214, -97.38373164638669),
        LatLng(18.449401690186576, -97.38380433155106),
        LatLng(18.451774775330364, -97.38556430769613),
        LatLng(18.45253910832089, -97.38613034773905),
        LatLng(18.454303658747108, -97.38536805167271),
        LatLng(18.45438261201187, -97.38732494647985),
        LatLng(18.464778167478954, -97.38703675269741),
        LatLng(18.464882552944985, -97.39142591745103),
        LatLng(18.469069415450633, -97.39137161938457),
                LatLng(18.469119120400038, -97.39292997980327),
        LatLng(18.475256979026753, -97.39274904517562),
        LatLng(18.475270207188473, -97.39405148089031),
        LatLng(18.474963811320876, -97.39585245773114),
        LatLng(18.472388767043313, -97.39477223502627),
        LatLng(18.470311082260636, -97.3939108273464),
        LatLng(18.469136139638522, -97.39391186186546),
        LatLng(18.469227826253203, -97.3999000526376),
        LatLng(18.46902865969649, -97.40017010572687),
        LatLng(18.46914327044759, -97.4060519656507),
        LatLng(18.470170630642684, -97.40702383436924),
        LatLng(18.47236141258496, -97.40884904070529),
        LatLng(18.473024805999813, -97.40954847247218),
        LatLng(18.47434231924045, -97.41100217575172),
        LatLng(18.47555226347427, -97.41242685819198),
        LatLng(18.477653267667534, -97.41464320185929),
        LatLng(18.47841837241988, -97.41539566688589),
        LatLng(18.478031554649263, -97.41591657722785),
        LatLng(18.476754046873822, -97.41461319895302),
        LatLng(18.476415838465144, -97.41509734880952),
        LatLng(18.47539536750284, -97.41403555254858),
        LatLng(18.475628993812563, -97.413708469311),
        LatLng(18.474569278789488, -97.4125729227212),
        LatLng(18.47497908408087, -97.41182991882296),
        LatLng(18.474055714633224, -97.41069634261807),
        LatLng(18.47284671285854, -97.40931914699074),
        LatLng(18.471910445495652, -97.40845994519253),
        LatLng(18.47049745482198, -97.40724906874634),
        LatLng(18.46939901742111, -97.40632067007157),
        LatLng(18.469074441938858, -97.40598192955538),
        LatLng(18.4683058984467, -97.40601128068208),
        LatLng(18.468273385213763, -97.40391177511866),
        LatLng(18.46830402602035, -97.40375025253167),
        LatLng(18.467476722324037, -97.40320107573736),
        LatLng(18.467515023509065, -97.40298302024563),
        LatLng(18.468907866499848, -97.4039068622217),
        LatLng(18.46967388284085, -97.40385033041368),
        LatLng(18.469528340123787, -97.4027842813424),

        LatLng(18.468276012886747, -97.40284958581597),
        LatLng(18.468221572462454, -97.40043693029737),
        LatLng(18.467386608126986, -97.40063883353086),
        LatLng(18.467224408378556, -97.39792183239717),
        LatLng(18.45892878743632, -97.3983227336975))
        // .color(Color.BLUE)
            .clickable(true);
        val polyline = map.addPolyline(polylineOptions)
        polyline.tag = "Ruta 13"
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
