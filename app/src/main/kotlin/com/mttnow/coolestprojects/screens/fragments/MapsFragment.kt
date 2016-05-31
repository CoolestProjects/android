package com.mttnow.coolestprojects.screens.fragments

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mttnow.coolestprojects.R

class MapsFragment : Fragment() {

    val TAG = javaClass.canonicalName

    companion object {
        fun newInstance(): MapsFragment {
            return MapsFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var rootView = inflater?.inflate(R.layout.fragment_maps, container, false)

        return rootView
    }
}