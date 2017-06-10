/**
 * Created by sarahd on 09/06/2017.
 */
package com.mttnow.coolestprojects.screens.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mttnow.coolestprojects.R
import rx.Subscription




class SponsorsFragment : BaseFragment() {
    override fun loadRxStuff(): Subscription? {
        return null;
    }


    companion object {
        fun newInstance(): SponsorsFragment {
            return SponsorsFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater?.inflate(R.layout.fragment_sponsors, container, false)


    }


}
