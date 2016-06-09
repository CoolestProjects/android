package com.mttnow.coolestprojects.screens.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mttnow.coolestprojects.R

class SpeakersFragment : Fragment() {

    val TAG = javaClass.canonicalName

    companion object {
        fun newInstance(): SpeakersFragment {
            return SpeakersFragment()
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_speakers, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }
}
