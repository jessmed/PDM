package com.medyesus.myapplication

import android.app.AlertDialog
import android.app.PendingIntent.getActivity
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.medyesus.myapplication.UserStartInfo.Companion.prefs
import com.medyesus.myapplication.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private val locale: Locale? = null
    private val GOOGLE_SIGN_IN = 100
    private val db = FirebaseFirestore.getInstance()



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        // Fijamos algunas preferencias más complejas como el idioma para que no se cargen las defaults
        checkInitState()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        val imageinicio: ImageView = findViewById(R.id.imageinicio)
        val profileicon: ImageView = findViewById(R.id.profileicon)
        var anclaje:View = findViewById(R.id.profileicon)


        setSupportActionBar(toolbar)


        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf(
                R.id.nav_home, R.id.nav_books,R.id.nav_activities), drawerLayout)

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        // Listener para botón de  tema en el menú lateral
        val switchLy: RelativeLayout =  navView.menu.getItem(3).actionView as RelativeLayout;
        val sw: Switch = switchLy.findViewById(R.id.cambiatema)

        sw.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

            } else {
                // The toggle is disabled
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        // Listener para botón de idioma en el menú lateral

        // Al iniciar comprobamos el idioma que tenemos guardado para visualizarlo correctamente
        val idiomaLy: RelativeLayout =  navView.menu.getItem(4).actionView as RelativeLayout;
        val bandera: ImageView = idiomaLy.findViewById(R.id.banderita2)

        // Fijamos el icono de bandera

        if(prefs.getLanguage() == "es"){
            bandera.setImageResource(R.mipmap.es_flag)
        }else{
            bandera.setImageResource(R.mipmap.en_flag)
        }


        val lang:MenuItem = navView.menu.getItem(4)
        lang.setOnMenuItemClickListener {
            val idiomaLy: RelativeLayout =  navView.menu.getItem(4).actionView as RelativeLayout;
            val bandera: ImageView = idiomaLy.findViewById(R.id.banderita2)


            // Cambiamos icono
            if(prefs.getLanguage() == "es"){
                bandera.setImageResource(R.mipmap.en_flag)
                prefs.saveLanguage("en")
                LocaleHelper.setLocale(this,"en")
                recreate()

            }else{
                bandera.setImageResource(R.mipmap.es_flag)
                prefs.saveLanguage("es")
                LocaleHelper.setLocale(this,"es")
                recreate()

            }
            true
        }




        // Método que al clicar en el logo vuelve navega al inicio
        imageinicio.setOnClickListener {
            val navController = findNavController(R.id.nav_host_fragment)
            navController.navigate(R.id.action_global_nav_home)
        }
        profileicon.setOnClickListener {
           showPopup(anclaje)
        }

    }

    // Función que checkea las preferencias iniciales de la app
    private fun checkInitState() {
        LocaleHelper.setLocale(this,prefs.getLanguage())
    }


    // Aqui podemos añadir opciones a la toolbar como el boton de settings, pero lo he quitado porque
    // no me gusta que esté ahí. Podríamos borrar la ejecución del menuInflater
//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        menuInflater.inflate(R.menu.main, menu)
//        return true
//    }




    // ##############################################################################################
    //                               FUNCIONES DEL MENÚ DERECHA
    // ##############################################################################################

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    // Funcion para le menú del perfil de la derecha, quería hacer uso de las funciones de visibilidad
    // pero despues de 4 horas no encuentro forma asi que he usado 2 menos que se intercambian
    fun showPopup(v: View) {
        val popup = PopupMenu(this, v)
        val inflater: MenuInflater = popup.menuInflater
        if(!prefs.getSession()) {
            inflater.inflate(R.menu.login_menu, popup.menu)
            popup.show()
        }
        else{
            inflater.inflate(R.menu.logout_menu, popup.menu)
            popup.show()
        }

        popup.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item: MenuItem? ->

            when (item!!.itemId) {
                R.id.login-> {
                    googleSigIn()
                }
                R.id.logout-> {
                    prefs.saveSession(false)

                }
            }
            true
        })
    }


    fun googleSigIn(){
        val googleConf = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        val googleClient = GoogleSignIn.getClient(this,googleConf)
        googleClient.signOut()  //Nos aseguramos de salir de una cuenta si hay una antes
        startActivityForResult(googleClient.signInIntent,GOOGLE_SIGN_IN)
    }

    // Comprobamos resultado de la iniciar sesión con google
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == GOOGLE_SIGN_IN){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)

            try {
                val account = task.getResult(ApiException::class.java)

                // Comprobamos si la cuenta que acabamos de recuperar es distinta de nula y si se accede con
                // exito guardamos la sesión en preferencias
                if(account != null) {
                    val credential = GoogleAuthProvider.getCredential(account.idToken,null)
                    FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener{
                        if (it.isSuccessful) {
                            prefs.saveSession(true)
                            prefs.saveEmail(FirebaseAuth.getInstance().currentUser.email)
                        }
                        else{
                            Toast.makeText(this, "ERROR", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } catch (e: ApiException){
                Toast.makeText(this, "ERROR", Toast.LENGTH_SHORT).show()
            }
        }
    }

}