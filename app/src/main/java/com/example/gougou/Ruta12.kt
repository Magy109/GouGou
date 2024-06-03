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

class Ruta12 : Fragment(), OnMapReadyCallback,
    GoogleMap.OnMyLocationButtonClickListener,
    GoogleMap.OnMyLocationClickListener {

    private var currentMarker: Marker? = null

    private lateinit var map: GoogleMap

    companion object {
        const val REQUEST_CODE_LOCATION = 0

        fun newInstance(): Ruta12 {
            return Ruta12()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_ruta12, container, false)
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
                LatLng(18.456652221709405, -97.39429536374999),
                LatLng(18.451807079728383, -97.39446033888795),
                LatLng(18.44998282438685, -97.39684578792235),
                LatLng(18.44996074468355, -97.39694554257744),
                LatLng(18.449862963105346, -97.39695551804307),
                LatLng(18.44979987818614, -97.39709849971536),
                LatLng(18.449871084346952, -97.39896823598363),
                LatLng(18.45002586564894, -97.40156356245848),
                LatLng(18.449293349666036, -97.40205965020257),
                LatLng(18.44177251730774, -97.40156435332362),
                LatLng(18.44076705505492, -97.40166327580835),
                LatLng(18.440563997093832, -97.39939343317961),
                LatLng(18.440243322602583, -97.39928271085981),
                LatLng(18.440158153188108, -97.3992128826012),
                LatLng(18.438978692918965, -97.39976048627237),
                LatLng(18.43916619217788, -97.40022520825848),
                LatLng(18.440548930672847, -97.40196297511415),
                LatLng(18.440835222652098, -97.40219212427859),
                LatLng(18.441578143216844, -97.40250718921827),
                LatLng(18.442382666046427, -97.40264517036918),
                LatLng(18.442930755973535, -97.40296541920858),
                LatLng(18.443932007452247, -97.40431907936053),
                LatLng(18.444408314287045, -97.40479457654966),
                LatLng(18.444288449170415, -97.40502401157251),
                LatLng(18.444449320828923, -97.40516034293425),
                LatLng(18.44463014727583, -97.40760746833581),
                LatLng(18.44647641241187, -97.40769579991147),
                LatLng(18.446926255743392, -97.41287742410435),
                LatLng(18.446314779348597, -97.41285874014784),
                LatLng(18.44565602697145, -97.41438766012902),
                LatLng(18.44752550514923, -97.41530890753974),
                LatLng(18.447095125613174, -97.41628445287824),
                LatLng(18.447166021216688, -97.41636385969328),
                LatLng(18.447157159268116, -97.41652734431221),
                LatLng(18.44734326009744, -97.41701312718013),
                LatLng(18.447339107919404, -97.41746716438584),
                LatLng(18.44739671051761, -97.41781748856927),
                LatLng(18.44733631036297, -97.4179091666242),
                LatLng(18.447393912962042, -97.41798857343919),
                LatLng(18.447814854445326, -97.41815205805815),
                LatLng(18.448391568720893, -97.41845842372261),
                LatLng(18.449799803335637, -97.41523559138282),
                LatLng(18.44794671699009, -97.41434465771849),
                LatLng(18.44853952190236, -97.41297170656469),
                LatLng(18.448415455569645, -97.41296703557569),
                LatLng(18.448417195375143, -97.41250683123637),
                LatLng(18.446904377530572, -97.41245310520088),
                LatLng(18.446787460947704, -97.41104274045055),
                LatLng(18.447290995287545, -97.41082683053916),
                LatLng(18.452112785459747, -97.41119575341293),
                LatLng(18.452143220605038, -97.41313338318761),
                LatLng(18.452978225543035, -97.41321979767879),
                LatLng(18.45496828069946, -97.41422210172038),
                LatLng(18.456434269739916, -97.41495400228521),
                LatLng(18.456834653962773, -97.4151510324312),
                LatLng(18.457184680866604, -97.41519307133319),
                LatLng(18.45692769915874, -97.41571622211407),
                LatLng(18.45727708825609, -97.41748391346924),
                LatLng(18.458821451305766, -97.41791074491616),
                LatLng(18.46061119419865, -97.41837578236158),
                LatLng(18.46054192697423, -97.41641727022528),
                LatLng(18.463238011300078, -97.41836989655935),
                LatLng(18.465977552828903, -97.42034510550457),
                LatLng(18.46942070603336, -97.42284554712415),
                LatLng(18.47060909524012, -97.42369846838719),
                LatLng(18.470724284799843, -97.42351629981175),
                LatLng(18.46843785227908, -97.42041971824814),
                LatLng(18.468087848328125, -97.41980314768476),
                LatLng(18.46714396953854, -97.41719671186073),
                LatLng(18.464899927117017, -97.4110482062877),
                LatLng(18.465006259481825, -97.41099682540771),
                LatLng(18.465519072601808, -97.41240550242432),
                LatLng(18.46628077573476, -97.4124020005399),
                LatLng(18.466267484291265, -97.41189286272652),
                LatLng(18.467786967073167, -97.41182165624933),
                LatLng(18.46776924530448, -97.41131718942495),
                LatLng(18.466050022163877, -97.407162250025),
                LatLng(18.46768499296604, -97.40707673473146),
                LatLng(18.467504027074554, -97.40293929347406),
                LatLng(18.46742444717691, -97.40186501293822),
                LatLng(18.46642215678213, -97.40192097708571),
                LatLng(18.46627712930551, -97.39945546231779),
                LatLng(18.46591825997062, -97.3994694752851),
                LatLng(18.465879585271324, -97.39904896914051),
                LatLng(18.463193660076385, -97.39917382452383),
                LatLng(18.463007276131222, -97.39405201824653),
                LatLng(18.46175941376967, -97.39408777147217),
                LatLng(18.46171852060941, -97.39322743998294),
                LatLng(18.46090675498523, -97.39326570636196),
                LatLng(18.460915616224042, -97.39342802323142),
                LatLng(18.45878017926711, -97.3935792856632),
                LatLng(18.458482361295196, -97.38514587998245),
                LatLng(18.45729049215707, -97.38524306845864),
                LatLng(18.457323416741957, -97.38556934540784),
                LatLng(18.45621055929537, -97.38559711354402),
                LatLng(18.456360380284366, -97.38913348818163),
                LatLng(18.456735723215317, -97.38949447395107),
                LatLng(18.456396259147922, -97.3895367989823),
                LatLng(18.456658635686622, -97.39429645428157))


        // .color(Color.BLUE)
            .clickable(true);
        val polyline = map.addPolyline(polylineOptions)
        polyline.tag = "Ruta 12"
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
