package com.medyesus.myapplication.ui.ideas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.medyesus.myapplication.R

class IdeasFragment : Fragment() {

    private lateinit var ideasViewModel: IdeasViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        ideasViewModel =
                ViewModelProvider(this).get(IdeasViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_ideas, container, false)

        return root
    }
}