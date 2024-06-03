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

class RutaUPN : Fragment(), OnMapReadyCallback,
    GoogleMap.OnMyLocationButtonClickListener,
    GoogleMap.OnMyLocationClickListener {

    private var currentMarker: Marker? = null

    private lateinit var map: GoogleMap

    companion object {
        const val REQUEST_CODE_LOCATION = 0

        fun newInstance(): RutaUPN {
            return RutaUPN()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_ruta_u_p_n, container, false)
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
                LatLng(18.42230065842054,-97.40751760073343),
                LatLng(18.42443978868947, -97.40663584984071),
                LatLng(18.424805348940268,-97.40621384234014),
                LatLng(18.425197018491943,-97.40625053748379),
                LatLng(18.42560609596717, -97.40635145232085),
                LatLng( 18.42564961479009,-97.40710372656075),
                LatLng( 18.426989988709792,-97.40704868207277),
                LatLng(18.427052909881724,-97.4056259954411),
                LatLng(18.428341051545388,-97.40564434359334),
                LatLng( 18.42832840768547,-97.40488467062686),
                LatLng(18.42898988213534,-97.4041690926909),
                LatLng(18.430180962242304,-97.40485397067334),
                LatLng(18.432967896179193, -97.4032106960075),
                LatLng( 18.434258784535942,-97.40506197884521),
                LatLng( 18.43709791351506,-97.40079622047789),
                LatLng(18.43772862432165,-97.40019560306855),
                LatLng(18.44211935223862,-97.3981816218961),
                LatLng( 18.445417848019503, -97.39670623359824),
                LatLng( 18.447643137772488,-97.39568814630215),
                LatLng( 18.44986815448935,-97.39466080877052),
                LatLng(  18.450887447909693,-97.3944138119259),
                LatLng( 18.451661967364544,-97.39439546377365),
                LatLng(18.4516031960637,-97.39261456939958),
                LatLng(18.453472360166643,-97.39258034848297),
                LatLng( 18.453454955457417, -97.39160789641666),
                LatLng(18.455265034730544,-97.39158037318106),
                LatLng( 18.456446030139574,-97.39253592348831),
                LatLng(18.458755797773705,-97.39248899095465),
                LatLng(18.458488485066894,-97.38514241781736),
                LatLng(18.45731369982569,-97.38522498058491),
                LatLng(18.457526941795933, -97.39252741771128),
                LatLng(18.457596558973037,-97.392655854777),
                LatLng( 18.457474728895022, -97.39273842146184),
                LatLng(18.45252995064682, -97.39282087654176),
                LatLng( 18.452668812688586,-97.3944586113564),
                LatLng(18.45138085337956,-97.39447695950865),
                LatLng(18.45045947998473, -97.39462306185999),
                LatLng(18.449466599976162,-97.39498638018851),
                LatLng(18.444690680260933,-97.39717405925859),
                LatLng( 18.442226492294097, -97.39825844726508),
                LatLng(18.440209686312514,-97.39919393933621),
                LatLng(18.437653524782107,-97.4003950776463),
                LatLng(18.43707041235382,-97.40106478520191),
                LatLng(18.434598216848713,-97.40475664484201),
                LatLng(18.434336462371107,-97.40514539918809),
                LatLng(18.43364019643893,-97.40430537819023),
                LatLng( 18.432987443272566, -97.40323201128629),
                LatLng( 18.43019510307721, -97.40487480076834),
                LatLng( 18.429014291989205, -97.40418686963282),
                LatLng(18.428349318231753,-97.40485108446715),
                LatLng(18.428349318231753, -97.40622719588232),
                LatLng(18.428396218513953,-97.40705241611339),
                LatLng(18.42561974078602,-97.40708911214134),
                LatLng(18.425592117715354,-97.40635309083451),
                LatLng(18.42517433641973,-97.4062338278452),
                LatLng(18.424808776953228,-97.4062338278452),
                LatLng(18.424434512885142,-97.40660996496541),
                LatLng(18.422685417638135,-97.40738496463207),
                LatLng(18.422279184527554,-97.40752205170229))
        // .color(Color.BLUE)
            .clickable(true);
        val polyline = map.addPolyline(polylineOptions)
        polyline.tag = "Ruta 7(UPN)"
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
