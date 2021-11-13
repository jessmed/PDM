package com.medyesus.mapffiti

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.preference.PreferenceManager
import android.content.SharedPreferences
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate


class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        supportActionBar?.hide()

        val backgroundImage: ImageView = findViewById(R.id.SplashImage)
        val backgroundText: TextView = findViewById(R.id.SplashText)

        val slideRightAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_right_animation)
        val slideAnimation = AnimationUtils.loadAnimation(this, R.anim.splash_animation)
        backgroundImage.startAnimation(slideAnimation)
        backgroundText.startAnimation(slideRightAnimation)
        val mp = MediaPlayer.create(this, R.raw.splashscreen_sound)
        mp.start()




        // Hacemos comprobaciones de tema y idioma

        // Idioma

        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        val idioma = preferences.getString("language", "")
        val tema = preferences.getBoolean("tema", false)

        if(idioma == "English" || idioma == "Inglés"){
            LocaleHelper.setLocale(this,"en")
        }
        if(idioma == "Spanish" || idioma == "Español"){
            LocaleHelper.setLocale(this,"es")
        }
        if(tema){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }



        // Lanzamos main activity
        Handler().postDelayed({
            val intent = Intent(this@SplashScreen, MainActivity::class.java)
            startActivity(intent)
            finish()
        },3000)

    }
}