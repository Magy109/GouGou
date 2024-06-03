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

class MapsFragment : Fragment(), OnMapReadyCallback,
    GoogleMap.OnMyLocationButtonClickListener,
    GoogleMap.OnMyLocationClickListener {

    private var currentMarker: Marker? = null

    private lateinit var map: GoogleMap

    companion object {
        const val REQUEST_CODE_LOCATION = 0

        fun newInstance(): MapsFragment {
            return MapsFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
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

        map.setOnMyLocationButtonClickListener(this)
        map.setOnMyLocationClickListener(this)

        enableLocation()

    }

    private fun createMarker() {

        val Tabarato=LatLng(18.460270545365574, -97.39144479198478)
        val bancos=LatLng(18.463481160405163, -97.39422058221102)
        val carmen=LatLng(18.464994285652924, -97.39375626920456)
        val martillo=LatLng(18.459188673244192, -97.39429845048343)
        val arter=LatLng(18.460959650071803, -97.39276421728115)
        val pizn=LatLng(18.45832491600418, -97.39148675521432)
        val paz=LatLng(18.461265572781596, -97.3895119977727)
        val ched=LatLng(18.463820218002756, -97.3869647556813)
        var cuidado=LatLng(18.462944043187075, -97.39434341890475)
        var ims=LatLng(18.47514330331415, -97.39534624923878)
        var japones=LatLng(18.46122345763858, -97.39421101643308)
        var corona=LatLng(18.460868153708233, -97.39008662785803)
        var milan=LatLng(18.463658798747097, -97.3913481914799)
        val sydney = LatLng(18.462323487879104, -97.39250785951711)
        map.addMarker(MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.b__1_)).anchor(0.0f,0.0f).position(Tabarato))
        map.addMarker(MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.b__1_)).anchor(0.0f,0.0f).position(bancos))
        map.addMarker(MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.b__1_)).anchor(0.0f,0.0f).position(pizn))
        map.addMarker(MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.b__1_)).anchor(0.0f,0.0f).position(carmen))
        map.addMarker(MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.b__1_)).anchor(0.0f,0.0f).position(ched))
        map.addMarker(MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.b__1_)).anchor(0.0f,0.0f).position(paz))
        map.addMarker(MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.b__1_)).anchor(0.0f,0.0f).position(arter))
        map.addMarker(MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.b__1_)).anchor(0.0f,0.0f).position(martillo))
        map.addMarker(MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.b__1_)).anchor(0.0f,0.0f).position(japones))
        map.addMarker(MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.b__1_)).anchor(0.0f,0.0f).position(ims))
        map.addMarker(MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.b__1_)).anchor(0.0f,0.0f).position(corona))
        map.addMarker(MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.b__1_)).anchor(0.0f,0.0f).position(cuidado))
        map.addMarker(MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.b__1_)).anchor(0.0f,0.0f).position(milan))
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,15f))
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
