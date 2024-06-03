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

class Ruta9 : Fragment(), OnMapReadyCallback,
    GoogleMap.OnMyLocationButtonClickListener,
    GoogleMap.OnMyLocationClickListener {

    private var currentMarker: Marker? = null

    private lateinit var map: GoogleMap

    companion object {
        const val REQUEST_CODE_LOCATION = 0

        fun newInstance(): Ruta9 {
            return Ruta9()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_ruta9, container, false)
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
                LatLng( 18.448385279645265,-97.418420654601),
                LatLng(18.44891782328368,-97.41721102176446),
                LatLng(18.44802994229424, -97.4167646966018),
                LatLng(18.449626888881454, -97.41298081138645),
                LatLng( 18.450285371762902,-97.41303551608327),
                LatLng(18.450754622319756, -97.41331832850736),
                LatLng(18.451890756666145,-97.41389526270207),
                LatLng(18.454407996525106, -97.41514238921377),
                LatLng(18.455458612479504,-97.41277491670628),
                LatLng(18.455573365225362, -97.41265956892464),
                LatLng(18.45691452522958,-97.409485388334),
                LatLng(18.45699393219124,-97.4023808427315),
                LatLng( 18.45627388422676,-97.40239583907037),
                LatLng( 18.456205627439573,-97.39666876975927),
                LatLng( 18.458866188421283,-97.39659974369577),
                LatLng(18.458725873530085,-97.39153444751473),
                LatLng(18.462919025451725,-97.39145624460015),
                LatLng( 18.462862238110745,-97.38940811101672),
                LatLng(18.458662571137708,-97.38948626183556),
                LatLng( 18.458502345203954,-97.38555648858846),
                LatLng(18.457310518814595, -97.3855741893942),
                LatLng(18.456193489700823,-97.38559916833503),
                LatLng( 18.45617726676592,-97.38529435364723),
                LatLng(18.4553370240359,-97.38534575509472),
                LatLng(18.454323073610652,-97.38437802485463),
                LatLng(18.451767990700205, -97.38559917441376),
                LatLng(18.449393224379378,-97.383811360388),
                LatLng(18.449457905348254,-97.38372612854275),
                LatLng(18.45051401577075,-97.38346059376389),
                LatLng( 18.449527044561904,-97.38033128880168),
                LatLng(18.44622721738648, -97.38150212772675),
                LatLng( 18.445078823107494,-97.37789630854432),
                LatLng(18.44467102404721,-97.37587068109376),
                LatLng(18.445267643614216,-97.37576709279608),
                LatLng(18.44532379593751,-97.3761370510012),
                LatLng(18.444748233754552,-97.37625543762711),
                LatLng( 18.445073238805506,-97.37788258789043),
                LatLng(18.44545541220249,-97.37906657040406),
                LatLng( 18.44653634001861,-97.3787040113633),
                LatLng(18.446803571978137,-97.37949386462773),
                LatLng(18.449010888375298, -97.37869847003051),
                LatLng(18.449788834036184, -97.38120865405297),
                LatLng( 18.45050622516193,-97.3834595561485),
                LatLng(18.44945505893837,-97.38372216035233),
                LatLng(18.449405926838793,-97.38381464990371),
                LatLng( 18.451774996474256,-97.38558293329974),
                LatLng(18.45302938027772,-97.38494949862041),
                LatLng(18.4543279956349,-97.38435910615145),
                LatLng(18.454614236679532,-97.38460035276411),
                LatLng( 18.456150650890038,-97.38450986065844),
                LatLng(18.456205788917046,-97.38559454315157),
                LatLng(18.45732810151881,-97.38556648844181),
                LatLng( 18.45732810151881,-97.38607331111774),
                LatLng( 18.46077116222429, -97.38605156566173),
                LatLng(18.46077116222429,-97.3871052233302),
                LatLng( 18.46479318220527,-97.38705620410215),
                LatLng(18.46484339863693,-97.38936743063506),
                LatLng( 18.456411099567276,-97.38954207761138),
                LatLng(18.456820685185505, -97.39746229275141),
                LatLng(18.45696518839786,-97.40013594607647),
                LatLng(18.4570494521589,-97.40236028919732),
                LatLng(18.45711903471873,-97.40262703797396),
                LatLng(18.457251331790204, -97.40909745640926),
                LatLng(18.456792480067378, -97.40987283105333),
                LatLng(18.45555307816319,-97.41273935574333),
                LatLng(18.45277451389309,-97.41909842151075),
                LatLng(18.452682595851257,-97.41907602108579),
                LatLng(18.44893372632349,-97.417223182684),
                LatLng( 18.44840298304321,-97.4184071496375))
        // .color(Color.BLUE)
            .clickable(true);
        val polyline = map.addPolyline(polylineOptions)
        polyline.tag = "Ruta 9"
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
