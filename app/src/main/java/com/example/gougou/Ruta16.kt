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

class Ruta16 : Fragment(), OnMapReadyCallback,
    GoogleMap.OnMyLocationButtonClickListener,
    GoogleMap.OnMyLocationClickListener {

    private var currentMarker: Marker? = null

    private lateinit var map: GoogleMap

    companion object {
        const val REQUEST_CODE_LOCATION = 0

        fun newInstance(): Ruta16 {
            return Ruta16()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_ruta16, container, false)
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
            LatLng(18.450297868595186, -97.40844663383706),
        LatLng(18.451929553661472, -97.40865541718978),
        LatLng(18.451957607025847, -97.4094210571447),
         LatLng(18.45482928815899, -97.40974847634702),
         LatLng(18.45678065096901, -97.40971761373319),
         LatLng(18.456927149415378, -97.40966175143689),
         LatLng(18.45725443241801, -97.40907684166172),
         LatLng(18.45726378421405, -97.40874167051423),
        LatLng(18.45947489002768, -97.40883707569195),
        LatLng(18.459870740656896, -97.40864648711366),
         LatLng(18.460226822409908, -97.4086362334204),
        LatLng(18.460323447027534, -97.40862966140064),
        LatLng(18.460222446879044, -97.40634328915704),
        LatLng(18.465503662247187, -97.40611050451295),
        LatLng(18.469048568514452, -97.40596953624872),
        LatLng(18.469129604166312, -97.40602211237248),
        LatLng(18.469049746147874, -97.40146926763735),
        LatLng(18.469021444255603, -97.40023160342821),
        LatLng(18.469059179966862, -97.40009235652357),
        LatLng(18.469188896410856, -97.39989094582175),
        LatLng(18.46512183822385, -97.40012516890106),
         LatLng(18.46488149407594, -97.39141197278649),
        LatLng(18.46291776785381, -97.39145124956728),
        LatLng(18.462967297711103, -97.39311660390692),
        LatLng(18.463008842901758, -97.3940618544557),
        LatLng(18.467038274530054, -97.39396544921601),
        LatLng(18.467231807812055, -97.39791875389533),
        LatLng(18.475063825386428, -97.39759201132995),
        LatLng(18.474957682136406, -97.39588923725583),
         LatLng(18.477047843383872, -97.39724585763133),

        LatLng(18.478007602311763, -97.40342313477443),

        LatLng(18.47799287705419, -97.40456012834832),
       LatLng(18.478345618641328, -97.40567499377948),
        LatLng(18.47845087150891, -97.40616584185648),
        LatLng(18.47871340908199, -97.40788879304831),
        LatLng(18.47904866153651, -97.40785888961851),
        LatLng(18.481475095951197, -97.40764819237565),
        LatLng(18.48044999673293, -97.4028014852254),
        LatLng(18.48041614962557, -97.40265873546474),
        LatLng(18.479832510425297, -97.39988209803187),
        LatLng(18.48074881091152, -97.39967872479151),
        LatLng(18.48079404888385, -97.39956456136431),
        LatLng(18.474995646418805, -97.39583485812302),
        LatLng(18.475001890438918, -97.39583705256072),
        LatLng(18.474970670339275, -97.39581510819448),
        LatLng(18.475251185807863, -97.39403459553232),
        LatLng(18.475255913478165, -97.39273413339382),
        LatLng(18.473328143038913, -97.39279197092051),
        LatLng(18.473336539567057, -97.39030389381243),
        LatLng(18.47130741342177, -97.39033896549098),
        LatLng(18.471310953580172, -97.38829023657702),
        LatLng(18.469840889772698, -97.38828906761375),
        LatLng(18.469847071962604, -97.3870188262737),
        LatLng(18.465894651366142, -97.38699074285883),
        LatLng(18.46359842346466, -97.38705455940193),
        LatLng(18.45737883144021, -97.38722814246707),
        LatLng(18.457316328267865, -97.3852196714047),
        LatLng(18.45848167092707, -97.385131694135),
        LatLng(18.458761089331958, -97.39249007529456),
        LatLng(18.45346358097393, -97.39258399408129),
        LatLng(18.453551237787124, -97.39673463093672),
        LatLng(18.450022875260075, -97.39684992067836),
        LatLng(18.44987677865197, -97.39675751300244),
        LatLng(18.449818339973575, -97.39685762131828),
        LatLng(18.4498621689838, -97.39696543027357),
        LatLng(18.44980373030046, -97.397127143707),
        LatLng(18.45014697337774, -97.40433295414282),
        LatLng(18.449935133408303, -97.40477959124432),
        LatLng(18.450297868595186, -97.40844663383706))
        // .color(Color.BLUE)
            .clickable(true);
        val polyline = map.addPolyline(polylineOptions)
        polyline.tag = "Ruta 15(purina)"
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
