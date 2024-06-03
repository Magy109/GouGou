package com.example.gougou//ruta 21
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

class Ruta42 : Fragment(), OnMapReadyCallback,
    GoogleMap.OnMyLocationButtonClickListener,
    GoogleMap.OnMyLocationClickListener {

    private var currentMarker: Marker? = null

    private lateinit var map: GoogleMap

    companion object {
        const val REQUEST_CODE_LOCATION = 0

        fun newInstance(): Ruta42 {
            return Ruta42()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_ruta42, container, false)
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
        LatLng(18.457325128374222, -97.38523925888038),
        LatLng(18.45733647680035, -97.38557424669145),
        LatLng(18.456218653748707, -97.38559817483676),
        LatLng(18.4563556543996, -97.38911125191541),
        LatLng(18.456741502650573, -97.38950007705286),
        LatLng(18.456418071087143, -97.38953596860426),
        LatLng(18.456507923051632, -97.39155773537365),
        LatLng(18.472149988239238, -97.39131133285987),
        LatLng(18.472253828587633, -97.39116495665094),
        LatLng(18.472411107762028, -97.3911616017068),
        LatLng(18.473680825055695, -97.38935396164565),
        LatLng(18.47734402691337, -97.38938822680609),
        LatLng(18.47722132203235, -97.39028289767316),
        LatLng(18.47879716575629, -97.38993813865208),
        LatLng(18.47866698111936, -97.38915317353673),
        LatLng(18.478549001114246, -97.38915746272612),
        LatLng(18.478451362505012, -97.38863844207565),
        LatLng(18.479463208769033, -97.38842847967597),
        LatLng(18.479451004010613, -97.38829979687034),
        LatLng(18.48789045577729, -97.38641515999319),
        LatLng(18.488548544852364, -97.38748334013454),
        LatLng(18.48907332050551, -97.38648390287426),
        LatLng(18.490403561539267, -97.38806334556097),
        LatLng(18.493888359702424, -97.38793377132845),
        LatLng(18.490433177181373, -97.3880639167253),
        LatLng(18.49001698185367, -97.38771277488223),
        LatLng(18.49069934347925, -97.38713489630359),
        LatLng(18.490701672840117, -97.38698261441692),
        LatLng(18.49232181108627, -97.3869014070415),
        LatLng(18.492366604699285, -97.3857071779109),
        LatLng(18.492816531452632, -97.38545064288832),
        LatLng(18.49284154405555, -97.3846084798571),
        LatLng(18.490513779731444, -97.3846062508924),
        LatLng(18.490891839611365, -97.38382253291068),
        LatLng(18.488220564939624, -97.38382361373198),
        LatLng(18.489140996623476, -97.38631768440248),
        LatLng(18.48851257836199, -97.38753906622156),
        LatLng(18.48661840035797, -97.38460829800148),
        LatLng(18.485475750159182, -97.38457512272305),
        LatLng(18.485450028878418, -97.38626730406288),
        LatLng(18.479325991958234, -97.38635194693545),
        LatLng(18.479345675313212, -97.38546663242225),
        LatLng(18.478605521633853, -97.38547270019824),
        LatLng(18.478540450116398, -97.3864043461448),
        LatLng(18.478416992798174, -97.38724230548956),
        LatLng(18.478494908731804, -97.38817856340968),
        LatLng(18.47856692547836, -97.38859076347563),
        LatLng(18.478456328318813, -97.38863686479884),
        LatLng(18.47854378019734, -97.38915211703663),
        LatLng(18.478672381473373, -97.38916025256414),
        LatLng(18.478788680919706, -97.38992012266274),
        LatLng(18.477217744023378, -97.39029265798469),
        LatLng(18.477328309946998, -97.38938686758512),
        LatLng(18.47370007819515, -97.38933866278667),
        LatLng(18.47241934764361, -97.39115493324161),
        LatLng(18.47251708811274, -97.39129866089623),
        LatLng(18.472475934237707, -97.39144510039333),
        LatLng(18.47233703983676, -97.39151018461402),
        LatLng(18.472226438659163, -97.39145865960582),
        LatLng(18.472174996226798, -97.39129594905337),
        LatLng(18.472205861688323, -97.3912037464073),
        LatLng(18.47013300691802, -97.38933030201281),
        LatLng(18.45866432216306, -97.38947590058449),
        LatLng(18.458487769821787, -97.38513373168118),
        LatLng(18.457325128374222, -97.38523925888038))

        // .color(Color.GREEN)
            .clickable(true);
        val polyline = map.addPolyline(polylineOptions)
        polyline.tag = "Ruta 21"

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
