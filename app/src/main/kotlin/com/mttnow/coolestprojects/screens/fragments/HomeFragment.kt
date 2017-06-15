package com.mttnow.coolestprojects.screens.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mttnow.coolestprojects.R
import android.text.method.LinkMovementMethod
import kotlinx.android.synthetic.main.home_beacon_layout.*
import kotlinx.android.synthetic.main.home_parking_layout.*
import kotlinx.android.synthetic.main.home_sponsors_edu_supp_layout.*

class HomeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        load_sponsors_btn?.setOnClickListener {
           swapFragment(SponsorsFragment())
        }

        parking_desc?.movementMethod = LinkMovementMethod.getInstance()
        home_beacon_layout?.setOnClickListener {
            //swapFragment(GemsFragment())
        }
        //TODO: Add in if get messages on gems page
//        view_gems_page_btn?.setOnClickListener {
//            //swapFragment(GemsFragment())
//        }
    }

    fun swapFragment(newFragment: Fragment) {
        val transaction = fragmentManager.beginTransaction()

        transaction.replace(R.id.fragment_container, newFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}
