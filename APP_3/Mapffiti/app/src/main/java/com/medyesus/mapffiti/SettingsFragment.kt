package com.medyesus.mapffiti

import android.os.Bundle
import android.widget.Button
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import java.lang.ref.Reference


class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

//        val language: ListPreference? = findPreference("language")
//        val f: String
//        val change = language?.onPreferenceChangeListener
//        if(change!!.onPreferenceChange(language,"English")){
//            LocaleHelper.setLocale(requireContext(),"en")
//        }




    }





}

//private fun ListPreference?.onPreferenceChangeListener(value: () -> Unit) {
//    if(value.toString() == "English" || value.toString() == "Inglés"){
//        LocaleHelper.setLocale(this!!.context,"en")
//    }
//    if(value.toString() == "Spanish" || value.toString() == "Español"){
//        LocaleHelper.setLocale(this!!.context,"es")
//    }
//    LocaleHelper.setLocale(this!!.context,"en")
//}
