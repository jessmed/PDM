package com.medyesus.myapplication.ui.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CreateViewModel : ViewModel() {
    val estilo = MutableLiveData<String>()

    fun select(name: String) {
        estilo.value = name
    }

    val talla = MutableLiveData<String>()

    fun select1(name: String) {
        talla.value = name
    }

    val color1 = MutableLiveData<Int>()

    fun select2(name: Int) {
        color1.value = name
    }

    val color2 = MutableLiveData<Int>()

    fun select3(name: Int) {
        color2.value = name
    }



}
