package com.medyesus.myapplication.ui.create

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.medyesus.myapplication.R
import com.medyesus.myapplication.databinding.FragmentCreate1Binding


class CreateFragment1 : Fragment() {

    private lateinit var createViewModel: CreateViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        createViewModel =
                ViewModelProvider(this).get(CreateViewModel::class.java)

        val binding = DataBindingUtil.inflate<FragmentCreate1Binding>(inflater,
            R.layout.fragment_create1,container,false)

        binding.textView3.setText(createViewModel.talla.value)

        binding.buttonCreate3.setOnClickListener { view : View ->
            if (binding.textView3.text==""){
                Toast.makeText(requireActivity(), R.string.toast2, Toast.LENGTH_SHORT).show()
            }else {
                view.findNavController().navigate(R.id.action_createFragment1_to_createFragment2)
            }
        }

        // Acciones botones elegir talla
        binding.button2.setOnClickListener { view : View ->
            binding.textView3.setText("XS")
            createViewModel.talla.value="XS"
        }
        binding.button3.setOnClickListener { view : View ->
            binding.textView3.setText("S")
            createViewModel.talla.value="S"
        }
        binding.button4.setOnClickListener { view : View ->
            binding.textView3.setText("S/M")
            createViewModel.talla.value="S/M"
        }
        binding.button5.setOnClickListener { view : View ->
            binding.textView3.setText("M/L")
            createViewModel.talla.value="M/L"
        }
        binding.button6.setOnClickListener { view : View ->
            binding.textView3.setText("L")
            createViewModel.talla.value="L"
        }
        binding.button7.setOnClickListener { view : View ->
            binding.textView3.setText("XL")
            createViewModel.talla.value="XL"
        }
        binding.button8.setOnClickListener { view : View ->
            binding.textView3.setText("XXL")
            createViewModel.talla.value="XXL"
        }

        return binding.root
    }
}