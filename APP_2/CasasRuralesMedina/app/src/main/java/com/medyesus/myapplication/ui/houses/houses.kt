package com.medyesus.myapplication.ui.houses

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.medyesus.myapplication.R
import com.medyesus.myapplication.databinding.HousesFragmentBinding

class houses : Fragment() {

    companion object {
        fun newInstance() = houses()
    }

    private lateinit var viewModel: House1ViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<HousesFragmentBinding>(inflater,
            R.layout.houses_fragment,container,false)

        binding.button12.setOnClickListener(){
            view?.findNavController()?.navigate(R.id.action_nav_houses_to_nav_house_1)
        }
        binding.button13.setOnClickListener(){
            view?.findNavController()?.navigate(R.id.action_nav_houses_to_nav_house_2)
        }
        binding.button14.setOnClickListener(){
            view?.findNavController()?.navigate(R.id.action_nav_houses_to_nav_house_3)
        }
        return binding.root
    }



}