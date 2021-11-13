package com.medyesus.myapplication.ui.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.medyesus.myapplication.R
import com.medyesus.myapplication.databinding.FragmentCreate3Binding

class CreateFragment3 : Fragment() {

    private lateinit var createViewModel: CreateViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        createViewModel =
                ViewModelProvider(this).get(CreateViewModel::class.java)

        // Guardamos objeto
        val binding = DataBindingUtil.inflate<FragmentCreate3Binding>(inflater,
            R.layout.fragment_create3,container,false)

        binding.button9.setOnClickListener {
            binding.textView3.setText(createViewModel.estilo.value)
            binding.textView6.setText(createViewModel.talla.value)
            createViewModel.color1.value?.let { binding.textView10.setBackgroundColor(it) }
            createViewModel.color2.value?.let { binding.textView7.setBackgroundColor(it) }
        }


        binding.buttonCreate4.setOnClickListener { view : View ->
            // Creamos objeto creacion y lo guardamos en el dispositivo
            //val creation1 = Creacion

            view.findNavController().navigate(R.id.action_createFragment3_to_nav_home)
        }

        return binding.root
    }
}