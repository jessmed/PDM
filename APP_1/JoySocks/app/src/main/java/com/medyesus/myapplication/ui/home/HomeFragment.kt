package com.medyesus.myapplication.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.github.dhaval2404.colorpicker.ColorPickerDialog
import com.github.dhaval2404.colorpicker.model.ColorShape
import com.medyesus.myapplication.MainActivity
import com.medyesus.myapplication.R
import com.medyesus.myapplication.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)


        val binding = DataBindingUtil.inflate<FragmentHomeBinding>(inflater,
            R.layout.fragment_home,container,false)

        binding.buttonCreate.setOnClickListener { view : View ->
            view.findNavController().navigate(R.id.action_nav_home_to_nav_create)
        }
        binding.buttonIdeas.setOnClickListener { view : View ->
            view.findNavController().navigate(R.id.action_nav_home_to_nav_ideas)
        }
        binding.buttonSocks.setOnClickListener { view : View ->
            view.findNavController().navigate(R.id.action_nav_home_to_nav_socks)
        }
        binding.buttonSettings.setOnClickListener { view : View ->
            view.findNavController().navigate(R.id.action_nav_home_to_nav_settings)
        }
        binding.buttonAboutUs.setOnClickListener { view : View ->
            view.findNavController().navigate(R.id.action_nav_home_to_nav_aboutus)
        }




        return binding.root
    }
}