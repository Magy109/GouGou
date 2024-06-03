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

class Ruta5 : Fragment(), OnMapReadyCallback,
    GoogleMap.OnMyLocationButtonClickListener,
    GoogleMap.OnMyLocationClickListener {

    private var currentMarker: Marker? = null

    private lateinit var map: GoogleMap

    companion object {
        const val REQUEST_CODE_LOCATION = 0

        fun newInstance(): Ruta5 {
            return Ruta5()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_ruta5, container, false)
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
                LatLng(18.477081553719174,-97.39749931691694),
                LatLng(18.472356256685785,-97.39769358745053),
                LatLng(18.47234722764985, -97.39870914790491),
                LatLng(18.469215965310113,-97.39886261179105),
                LatLng(18.469191002640088,-97.39638653154347),
                LatLng(18.46713742492078, -97.39641421496651),
                LatLng(18.467319812341785,-97.39999145395915),
                LatLng( 18.46117415259438,-97.40032309453075),
                LatLng(18.461112796778423,-97.40022606767197),
                LatLng(18.460890010652477, -97.39256239823955),
                LatLng(18.46083479032815, -97.39251711903887),
                LatLng(18.457512783717192,-97.39250043537945),
                LatLng( 18.457500512410775,-97.39153340108683),
                LatLng(18.457005942971122,-97.39154538317186),
                LatLng( 18.456917359551284,-97.38951147678762),
                LatLng(18.455466252176777,-97.38953868883644),
                LatLng(18.455435573341205,-97.38841964645724),
                LatLng( 18.449536174097844,-97.38388199755538),
                LatLng(18.447473409969234,-97.3827266091992),
                LatLng(  18.445426676734414,-97.38160799793023),
                LatLng( 18.444752132126624,-97.38139309291597),
                LatLng(18.442728059505384,-97.38066087037924),
                LatLng(18.441082156791836,-97.38003378578597),
                LatLng(18.43698377553217,-97.37845266907912),
                LatLng(18.436046573640297,-97.38098223798865),
                LatLng(18.435226015982195,-97.38033616976108),
                LatLng(18.435187662806783,-97.38041055701937),
                LatLng(18.43430469019151, -97.38005416465069),
                LatLng(18.435150439719337,-97.37773476102015),
                LatLng(18.434203090632664,-97.37737915921696),
                LatLng(18.434782425059424,-97.37567174352527),
                LatLng(18.433064713140638,-97.37504818917648),
                LatLng(18.432506092872657,-97.37672096176023),
                LatLng(18.431148364849534,-97.37620034449816),
                LatLng( 18.431504305926097,-97.37517831426825),
                LatLng(18.429816080631085,-97.3745406054525),
                LatLng(18.430429227369032,-97.37281763398713),
                LatLng(18.430044873119243, -97.37265704802994),
                LatLng(18.429473319987892,-97.37440954083675),
                LatLng( 18.42911076196559, -97.37427897244358),
                LatLng(18.4289137218465,-97.37484058511377),
                LatLng( 18.428419757412797,-97.37465657390288),
                LatLng(18.428292419795, -97.3750349786521),
                LatLng( 18.42841975732601, -97.37465819111942),
                LatLng(18.428912924729516,-97.37482555266823),
                LatLng(18.429102785852393, -97.37427414353047),
                LatLng(18.42946720533429,-97.37438876994901),
                LatLng(18.430085537413106,-97.37256100366483),
                LatLng(18.430485914001196,-97.37272188338667),
                LatLng(18.429839386556694,-97.37452811429566),
                LatLng(18.43152880850539,-97.37517084304548),
                LatLng(18.431177150901902,-97.37619259418848),
                LatLng(   18.43249651533438,-97.37669874414563),
                LatLng(18.43306418718727,-97.37503447143851),
                LatLng(18.4348865053352,-97.37570373692783),
                LatLng( 18.43427264527172,-97.37739212031109),
                LatLng(18.435164984941636,-97.3777544511271),
                LatLng(18.43466173844162,-97.37912671678461),
                LatLng(18.43645126615793,-97.37985588529287),
                LatLng(18.436978164749505,-97.37843483633222),
                LatLng(18.438989361827396,-97.37920941792311),
                LatLng(18.440348361371647,-97.37977057270696),
                LatLng(18.444302659054145, -97.38128676901849),
                LatLng(18.4454684451877,-97.38160698995956),
                LatLng( 18.449533379446564,-97.38389489369797),
                LatLng(18.45301768103174,-97.38650378345757),
                LatLng(18.456797607789312, -97.38952635584832),
                LatLng(18.460836576351127,-97.38946892533674),
                LatLng(18.460691408151504,-97.38504587585305),
                LatLng( 18.464718934944017,-97.38474016439643),
                LatLng(18.46460739725275,-97.38059179104923),
                LatLng(18.46577412210938,-97.38055131212909),
                LatLng(18.46591552927923,-97.38463246331958),
                LatLng(18.46782578395468, -97.38444655489606),
                LatLng(18.46791416254112,-97.38426020075312),
                LatLng(18.469183183385525,-97.38393746457699),
                LatLng(  18.46898875320896,-97.38704957815695),
                LatLng( 18.466867671404614,-97.38702162470445),
                LatLng(18.46705326964843,-97.39396330970874),
                LatLng(18.474352001940517,-97.39381177087706),
                LatLng(18.474307814311516, -97.3955169112864),
                LatLng(  18.477065099685177, -97.39717544782371),
                LatLng( 18.47708277445453,-97.39749224986653))
          //  .color(Color.MAGENTA)
            .clickable(true);
        val polyline = map.addPolyline(polylineOptions)
        polyline.tag = "Ruta 5"
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
