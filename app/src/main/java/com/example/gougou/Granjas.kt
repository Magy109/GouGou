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

class Granjas : Fragment(), OnMapReadyCallback,
    GoogleMap.OnMyLocationButtonClickListener,
    GoogleMap.OnMyLocationClickListener {

    private var currentMarker: Marker? = null

    private lateinit var map: GoogleMap

    companion object {
        const val REQUEST_CODE_LOCATION = 0

        fun newInstance(): Granjas {
            return Granjas()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_granjas, container, false)
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
                LatLng(18.423669473735657, -97.38289577500193),
                LatLng(  18.42407469956511,-97.38422730798048),
                LatLng(18.424933113051026, -97.38600195255539),
                LatLng(18.425426425780344,-97.38669995591418),
                LatLng(18.42579809394323,-97.38762528955654),
                LatLng( 18.426083349934473,-97.38872780627595),
                LatLng(18.426515776760837,-97.38990206248808),
                LatLng(18.427106828704865,-97.3917530266642),
                LatLng( 18.42720574828607,-97.39281506005413),
                LatLng( 18.427759256343165, -97.39432923664556),
                LatLng(18.428202423014696,-97.39547983644847),
                LatLng(18.42842842687159, -97.39613586862554),
                LatLng(18.429346276269925,-97.39751669554043),
                LatLng(18.4301426391835,-97.39845180565848),
                LatLng(18.43042301939414, -97.39903914157324),
                LatLng(18.430945750743845,-97.40055366691126),
                LatLng(18.4316295942738,-97.40150364415786),
                LatLng( 18.431750263192498,-97.40163083795468),
                LatLng( 18.432211857690206,-97.40181768123603),
                LatLng(18.432383222798492,-97.40206991283114),
                LatLng(18.432713286133634,-97.40282559362605),
                LatLng(18.433678088612282,-97.40433978954516),
                LatLng(18.434262681848324,-97.40507436065117),
                LatLng(18.43694435916889,-97.40106243852699),
                LatLng(18.437566008033556,-97.40032611723544),
                LatLng( 18.438554460504903,-97.39981836912268),
                LatLng( 18.43929549159523,-97.39949387935884),
                LatLng(18.44180351827781, -97.39835591296277),
                LatLng(18.443023541478183,-97.39780321366521),
                LatLng(18.444657001941593,-97.39706075722756),
                LatLng(18.44602909751886,-97.39642229213062),
                LatLng(18.449088937106154,-97.39502766030704),
                LatLng(18.450004344220474,-97.39464779791447),
                LatLng(18.450529054078714,-97.39448692629325),
                LatLng(18.451217499650966,-97.39439340148076),
                LatLng(18.451643340548387, -97.39440088349805),
                LatLng(18.4515968065475,-97.39260689738781),
                LatLng(18.452509472683644, -97.39261582409601),
                LatLng(18.452346234438977,-97.39202474703862),
                LatLng( 18.452573348476292, -97.39194244516987),
                LatLng(18.45261238367148, -97.39164690664117),
                LatLng(18.45332312608285,-97.39159554404736),
                LatLng(18.455188067990974,-97.39158102006473),
                LatLng( 18.456436691703644, -97.39252503702046),
                LatLng(18.458769113927545,-97.39249589848188),
                LatLng( 18.458768676197664,-97.39249877528268),
                LatLng(18.458482867431798,-97.38512764285898),
                LatLng( 18.457297655344888,-97.3852174245686),
                LatLng( 18.4574223616561, -97.38918607062382),
                LatLng(18.45752036609386, -97.39250545371742),
                LatLng(18.45758424002244,-97.39255408663985),
                LatLng( 18.45758424002244,-97.3927336543534),
                LatLng( 18.452522526583763,-97.39281246694487),
                LatLng(18.452678668250826,-97.3944435369501),
                LatLng(  18.451411786018326, -97.39448151900447),
                LatLng( 18.45045364015131,-97.39459000783143),
                LatLng(18.44883343762477, -97.3952595852375),
                LatLng(18.446486270509993, -97.39633128561175),
                LatLng( 18.443668528259835, -97.3976181637213),
                LatLng(18.441176174071586,-97.39875042748434),
                LatLng(18.439449553344858,-97.39954263481316),
                LatLng(18.437680248873207,-97.40037964950481),
                LatLng(18.43737796951551,-97.4006536429822),
                LatLng(18.437033719236155, -97.40114745419484),
                LatLng( 18.436408530298692,-97.40199136951782),
                LatLng(18.435072300018334,-97.40402908718767),
                LatLng( 18.434343487828826, -97.40511446794402),
                LatLng( 18.43384307372159, -97.4045944697732),
                LatLng( 18.432997928187916, -97.40326095143408),
                LatLng( 18.432532470802713, -97.40247724019675),
                LatLng( 18.432315977490887, -97.40193479606133),
                LatLng(18.432202407120116,-97.40181508425226),
                LatLng( 18.43174102671675,-97.40161307057441),
                LatLng( 18.43091408747607, -97.40052303108412),
                LatLng(18.43070645439043,-97.39994870486149),
                LatLng(18.43034799429634,-97.39884885261495),
                LatLng(18.430140554347048, -97.39844521219054),
                LatLng(18.42932070378616,-97.39749125509418),
                LatLng(18.428401475429496,-97.39606967736076),
                LatLng( 18.42726574650625, -97.39309560086798),
                LatLng(18.42709538615172, -97.3917488537279),
                LatLng(18.426399790386128, -97.38954406918816),
                LatLng( 18.426059066805777, -97.38866867588946),
                LatLng(18.425718343070045, -97.38738925592934),
                LatLng( 18.425429505827182, -97.38670928538482),
                LatLng(18.42492551655002,-97.38598727353619),
                LatLng(18.424151563362955,-97.38440062581117),
                LatLng(18.42365559448183,-97.38288991018419))
        // .color(Color.BLUE)
            .clickable(true);
        val polyline = map.addPolyline(polylineOptions)
        polyline.tag = "Ruta 7(Granjas)"
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
