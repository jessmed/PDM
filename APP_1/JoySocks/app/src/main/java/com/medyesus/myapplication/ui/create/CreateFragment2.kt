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
import com.github.dhaval2404.colorpicker.ColorPickerDialog
import com.github.dhaval2404.colorpicker.model.ColorShape
import com.medyesus.myapplication.R
import com.medyesus.myapplication.databinding.FragmentCreate2Binding


class CreateFragment2 : Fragment() {

    private lateinit var createViewModel: CreateViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        createViewModel =
            ViewModelProvider(this).get(CreateViewModel::class.java)

        val binding = DataBindingUtil.inflate<FragmentCreate2Binding>(inflater,
            R.layout.fragment_create2,container,false)

        createViewModel.color1.value?.let { binding.textView3.setBackgroundColor(it) }
        createViewModel.color2.value?.let { binding.textView6.setBackgroundColor(it) }

        binding.buttonCreate4.setOnClickListener { view : View ->
            if (binding.textView3.background==null && binding.textView6.background==null){
                Toast.makeText(requireActivity(), R.string.toast5, Toast.LENGTH_SHORT).show()
            }else if(binding.textView3.background==null ){
                Toast.makeText(requireActivity(), R.string.toast3, Toast.LENGTH_SHORT).show()
            }else if(binding.textView6.background==null){
                Toast.makeText(requireActivity(), R.string.toast4, Toast.LENGTH_SHORT).show()
            } else {
                view.findNavController().navigate(R.id.action_createFragment2_to_createFragment3)
            }
        }

        binding.button.setOnClickListener {
            ColorPickerDialog
                .Builder(requireActivity())        				// Pass Activity Instance
                .setTitle("Pick color")           	            // Default "Choose Color"
                .setColorShape(ColorShape.SQAURE)               // Default ColorShape.CIRCLE
                .setDefaultColor("#000000")                     // Pass Default Color
                .setColorListener { color, colorHex ->
                    binding.textView3.setBackgroundColor(color)
                    createViewModel.color1.value=color
                }
                .show()


        }

        binding.button11.setOnClickListener {
            ColorPickerDialog
                .Builder(requireActivity())        				// Pass Activity Instance
                .setTitle("Pick color")           	            // Default "Choose Color"
                .setColorShape(ColorShape.SQAURE)               // Default ColorShape.CIRCLE
                .setDefaultColor("#000000")                     // Pass Default Color
                .setColorListener { color, colorHex ->
                    binding.textView6.setBackgroundColor(color)
                    createViewModel.color2.value=color
                }
                .show()
        }
        return binding.root
    }
}