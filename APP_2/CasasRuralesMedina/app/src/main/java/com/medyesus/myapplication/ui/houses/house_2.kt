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
import com.medyesus.myapplication.databinding.House2FragmentBinding
import org.imaginativeworld.whynotimagecarousel.CarouselItem
import org.imaginativeworld.whynotimagecarousel.ImageCarousel

class house_2 : Fragment() {

    private val list = mutableListOf<CarouselItem>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<House2FragmentBinding>(inflater,
            R.layout.house_2_fragment,container,false)

        binding.btnBook2.setOnClickListener(){
            view?.findNavController()?.navigate(R.id.action_nav_house_2_to_bookPageFragment)
        }
        val carousel: ImageCarousel = binding.carousel2
        list.add(CarouselItem(R.mipmap.foto_1))
        list.add(CarouselItem(R.mipmap.foto_2))
        list.add(CarouselItem(R.mipmap.foto_3))
        list.add(CarouselItem(R.mipmap.foto_4))
        list.add(CarouselItem(R.mipmap.foto_5))
        list.add(CarouselItem(R.mipmap.foto_6))
        list.add(CarouselItem(R.mipmap.foto_7))
        list.add(CarouselItem(R.mipmap.foto_8))

        carousel.addData(list)
        return binding.root
    }
}