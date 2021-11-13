package com.medyesus.mapffiti

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.preference.PreferenceManager
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.medyesus.mapffiti.MainActivity.Companion.REQUEST_CODE_LOCATION


class MapsFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    private lateinit var  map: GoogleMap
    private lateinit var  mapV: MapView
    private lateinit var  viewM: View

    private lateinit var dialogBuilder: AlertDialog.Builder
    private lateinit var dialog: AlertDialog


    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapV = view.findViewById(R.id.mapsView)


        if(mapV != null){
            mapV.onCreate(null)
            mapV.onResume()
            mapV.getMapAsync(this)
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        //MapsInitializer.initialize(requireContext())
        map = googleMap
        createMarker()
        enableLocation()  //Activamos la localizacón

        // Checkeamos si estamos en modo oscuro para cambiar el mapa
        val preferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        val tema = preferences.getBoolean("tema", false)

        if(tema){
            val mapStyleOptions = MapStyleOptions.loadRawResourceStyle(requireContext(), R.raw.mapstyle )
            map.setMapStyle(mapStyleOptions)
        }
    }

    // Función que modela el comportamiento al clicar en la ventana de un marker
    override fun onInfoWindowClick(m: Marker) {
        createPopUpWindow(m.title,m.snippet,)
    }

    // Función para crear un marcador a partir de las coordenadas y un titulo
    private fun createMarker() {
        val coordinates = LatLng(37.186697, -3.610539)
        val coordinates2 = LatLng(37.187449, -3.609816)
        val coordinates3 = LatLng(37.187453, -3.611554)
        val coordinates_start = LatLng(37.187133, -3.610760)


        val marker = MarkerOptions()
            .position(coordinates)
            .title("Girl with balloon")
            .snippet("Bansky")

        val marker2 = MarkerOptions()
            .position(coordinates2)
            .title("Smiling Cats")
            .snippet("Unknown")

        val marker3 = MarkerOptions()
            .position(coordinates3)
            .title("Jumping Fox")
            .snippet("Unknown")

        map.addMarker(marker)
        map.addMarker(marker2)
        map.addMarker(marker3)
        map.setOnInfoWindowClickListener(this)  //Seteamos el listener del click ventana del marker
        map.animateCamera(
            CameraUpdateFactory.newLatLngZoom(coordinates_start, 18f),
            1,
            null
        )

    }

    private fun isLocationPermissionGranted() = ContextCompat.checkSelfPermission(
        requireContext(),
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED

    private fun enableLocation() {
        // si el mapa no esta creado salta este método
        if (!::map.isInitialized) return
        if (isLocationPermissionGranted()) {
            //aceptados
            map.isMyLocationEnabled = true
        } else {
            // no aceptados
            requestLocationPermission()
        }
    }

    private fun requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        ) {
            Toast.makeText(requireContext(), R.string.mensaje_permisos, Toast.LENGTH_SHORT).show()
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
        when(requestCode){
            REQUEST_CODE_LOCATION -> if(grantResults.isNotEmpty() && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                map.isMyLocationEnabled = true
            }else{
                Toast.makeText(requireContext(), R.string.mensaje_permisos_2, Toast.LENGTH_SHORT).show()
            }else -> {}
        }
    }


    private fun createPopUpWindow(title:String, author:String){

        dialogBuilder = AlertDialog.Builder(context)

        dialogBuilder.setView(R.layout.popup_window)

        dialog = dialogBuilder.create()

        dialog.show()

        val dialogTitle = dialog.findViewById<TextView>(R.id.dialog_title)
        dialogTitle.text = title

        val autorBtn = dialog.findViewById<Button>(R.id.author_btn)
        val dialogDescription = dialog.findViewById<TextView>(R.id.description)
        autorBtn.text = "#" + author

        val dialogImage= dialog.findViewById<ImageView>(R.id.image)
        val collectionBtn = dialog.findViewById<Button>(R.id.collection_btn)
        val themeBtn = dialog.findViewById<Button>(R.id.theme_btn)



        if(title == "Smiling Cats"){
            dialogImage.setImageResource(R.mipmap.cats)
            dialogDescription.text = getString(R.string.descripccion_1)
            collectionBtn.text = getString(R.string.tag_animals)
            themeBtn.text = getString(R.string.tag_cartoon)

        }
        if(title == "Girl with balloon"){
            dialogImage.setImageResource(R.mipmap.g1_1)
            dialogDescription.text = getString(R.string.descripccion_2)
            collectionBtn.text = getString(R.string.tag_people)
            themeBtn.text = getString(R.string.tag_realistic)
        }
        if(title == "Jumping Fox"){
            dialogImage.setImageResource(R.mipmap.fox)
            dialogDescription.text = getString(R.string.descripccion_3)
            collectionBtn.text = getString(R.string.tag_animals)
            themeBtn.text = getString(R.string.tag_realistic)
        }






        //Listener boton cerrar
        val closeBtn = dialog.findViewById<Button>(R.id.close_btn)
        closeBtn.setOnClickListener(){
            dialog.dismiss()
        }

        //Listener boton autor

        autorBtn.setOnClickListener(){
            dialog.dismiss()
            view?.findNavController()?.navigate(R.id.action_global_ExploreFragment)
        }

        //Listener boton tema
        themeBtn.setOnClickListener(){
            dialog.dismiss()
            view?.findNavController()?.navigate(R.id.action_global_ExploreFragment)
        }

        //Listener boton collection
        collectionBtn.setOnClickListener(){
            dialog.dismiss()
            view?.findNavController()?.navigate(R.id.action_global_ExploreFragment)
        }

        //Listener boton check
        val checkBtn = dialog.findViewById<Button>(R.id.check_btn)
        checkBtn.setOnClickListener(){
            dialog.dismiss()
        }
    }


}