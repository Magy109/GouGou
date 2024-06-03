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

class Ruta11 : Fragment(), OnMapReadyCallback,
    GoogleMap.OnMyLocationButtonClickListener,
    GoogleMap.OnMyLocationClickListener {

    private var currentMarker: Marker? = null

    private lateinit var map: GoogleMap

    companion object {
        const val REQUEST_CODE_LOCATION = 0

        fun newInstance(): Ruta11 {
            return Ruta11()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_ruta11, container, false)
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
               LatLng(18.462893178736408,-97.38940443348602),
                LatLng( 18.462810006223137, -97.38709458710395),
                LatLng( 18.469007654864697, -97.38702789603317),
                LatLng(   18.46919343737676, -97.38395331526115),
                LatLng(18.469083039815004, -97.38395331526115),
                LatLng(18.467928879240176,-97.38424958687328),
                LatLng(18.46783696165548,-97.3844509623976),
                LatLng( 18.465907185767207, -97.38463864407467),
                LatLng(18.465453067354858, -97.37374859654366),
                LatLng( 18.467837668593802, -97.37339695136085),
                LatLng(18.46545597996672,-97.37373054889598),
                LatLng(18.465501143568133,-97.37424902421677),
                LatLng(18.46427670398117,-97.37432309210416),
                LatLng( 18.46485553859513,-97.38934451866778),
                LatLng(18.457437973399124, -97.38949745007372),
                LatLng(18.457520463621492, -97.39249562163445),
                LatLng(18.457457733475877,-97.39255117256178),
                LatLng( 18.457462751888798, -97.39272840647244),
                LatLng( 18.456554336369592, -97.39274955707432),
                LatLng( 18.456849724976465,-97.39755552649581),
                LatLng( 18.457059693368663,-97.40236599853114),
                LatLng( 18.456267865868796, -97.4023831115238),
                LatLng( 18.456294773401297, -97.40363650683402),
                LatLng( 18.455984231226637, -97.40488316314705),
                LatLng( 18.453784261655855,-97.40479942714083),
                LatLng(18.453768209029874, -97.4040785155141),
                LatLng( 18.45362373547171,-97.40356068155396),
                LatLng(18.452994470817075,-97.40352345137626),
                LatLng(18.452978927416623,-97.40358960653306),
                LatLng( 18.4510550262803,-97.40344726707772),
                LatLng(18.45132471433257,-97.40470293858111),
                LatLng(18.452110239327695,-97.40471822638898),
                LatLng(18.452614089814844,-97.40476598273538),
                LatLng( 18.453789203408505, -97.40478549484183),
                LatLng(18.453773901507034, -97.40658272672582),
                LatLng(18.451940927472748, -97.40635136385681),
                LatLng(18.451931156557706, -97.40865311531257),
                LatLng(18.453396348243643,-97.40883014030918),
                LatLng(18.453462700634248,-97.40959693027587),
                LatLng(18.454837033717908, -97.4097607099718),
                LatLng(18.45678026143085,-97.40970679213943),
                LatLng(18.45745329935221,-97.40965151667439),
                LatLng(18.460277629839183, -97.40953772667768),
                LatLng(18.46015595213548,-97.40686951127275),
                LatLng(18.463429523553174,-97.40672003792635),
                LatLng(18.463256798204384,-97.40120057209047),
                LatLng( 18.463269639595197, -97.40095688365346),
                LatLng( 18.46310896338602, -97.3964973089167),
                LatLng( 18.465015303153436, -97.3964345668293),
                LatLng(18.465086129412214,-97.39907313880141),
                LatLng( 18.465876792714496, -97.39904013388195),
                LatLng( 18.465867439052587,-97.3986020388687),
                LatLng( 18.466578271674905, -97.39855582508262),
                LatLng(18.466623215669898, -97.39897889528622),
                LatLng(18.472348781149833, -97.39871445025358),
                LatLng(18.472345569775044, -97.39769908366426),
                LatLng( 18.47505159927853, -97.39760733790834),
                LatLng(18.477084843789854,-97.39749121530062),
                LatLng(18.477049532038606, -97.39723940468416),
                LatLng(18.47429523608919,-97.39555071471003),
                LatLng( 18.474334952596507, -97.39276155615269),
                LatLng(18.475253195796682,-97.39273865153866),
                LatLng(18.475262826700785,-97.39379125060476),
                LatLng(18.476131213704136,-97.39376893887525),
                LatLng( 18.476157444812088, -97.39270012990569),
                LatLng(18.477364048239522,-97.39267404180355),
                LatLng(18.477558516612234, -97.3906562677927),
                LatLng(18.477536046049295,-97.39021289021952),
                LatLng( 18.477192567073857,-97.39030088882186),
                LatLng(18.474324703055544,-97.3903159962335),
                LatLng( 18.4743385150515,-97.38933963569316),
                LatLng( 18.473700699019318,-97.38934640512166),
                LatLng(18.472407842762493,-97.39116511904632),
                LatLng( 18.472510568385175, -97.39130388607289),
                LatLng(18.472462415756752,-97.39145619134639),
                LatLng(18.47232116797045,-97.39150357520909),
                LatLng(18.472234493135304,-97.39147988327755),
                LatLng(18.472173499705974, -97.39132080888096),
                LatLng(18.47220881169406, -97.39120911834748),
                LatLng(18.470133554221462,-97.3893274449576),
                LatLng( 18.464860423357464, -97.3893423514257))


        // .color(Color.BLUE)
            .clickable(true);
        val polyline = map.addPolyline(polylineOptions)
        polyline.tag = "Ruta 11"
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
