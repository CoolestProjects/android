package com.mttnow.coolestprojects.screens.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mttnow.coolestprojects.R
import com.mttnow.coolestprojects.network.CoolestProjectsService
import com.mttnow.coolestprojects.screens.home.mvp.HomeView
import kotlinx.android.synthetic.main.home_sponsors_layout.*
import rx.Subscription
import javax.inject.Inject
import android.R.attr.button



class HomeFragment : BaseFragment() {
    override fun loadRxStuff(): Subscription? {
        return null;
    }
    @Inject
    lateinit var homeView: HomeView

    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater?.inflate(R.layout.fragment_home, container, false)


    }


}
