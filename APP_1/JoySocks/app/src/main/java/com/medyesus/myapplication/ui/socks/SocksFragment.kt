package com.medyesus.myapplication.ui.socks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.medyesus.myapplication.R

class SocksFragment : Fragment() {

    private lateinit var socksViewModel: SocksViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        socksViewModel =
                ViewModelProvider(this).get(SocksViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_socks, container, false)

        return root
    }
}