package com.medyesus.myapplication.ui.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.github.dhaval2404.colorpicker.ColorPickerDialog
import com.github.dhaval2404.colorpicker.model.ColorShape
import com.medyesus.myapplication.R
import com.medyesus.myapplication.databinding.FragmentCreateBinding

class CreateFragment : Fragment() {

    private lateinit var createViewModel: CreateViewModel
    private var A = "A"
    private var B = "B"
    private var C = "C"
    private var D = "D"

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        createViewModel =
                ViewModelProvider(this).get(CreateViewModel::class.java)
        val binding = DataBindingUtil.inflate<FragmentCreateBinding>(inflater,
            R.layout.fragment_create,container,false)

        binding.textView3.setText(createViewModel.estilo.value)


        // Listener de boton next
        binding.buttonCreate2.setOnClickListener { view : View ->

            if (binding.textView3.text==""){
                Toast.makeText(requireActivity(), R.string.toast1, Toast.LENGTH_SHORT).show()
            }else {
                view.findNavController().navigate(R.id.action_nav_create_to_createFragment1)
            }
        }

        // Listeners de imágenes(opciones)
        binding.sockView1.setOnClickListener { view : View ->
            binding.textView3.setText(A)
            createViewModel.estilo.value=A
        }
        binding.sockView.setOnClickListener { view : View ->
            binding.textView3.setText(B)
            createViewModel.estilo.value=B
        }
        binding.sockView3.setOnClickListener { view : View ->
            binding.textView3.setText(C)
            createViewModel.estilo.value=C
        }
        binding.sockView4.setOnClickListener { view : View ->
            binding.textView3.setText(D)
            createViewModel.estilo.value=D
        }

        return binding.root

    }
}