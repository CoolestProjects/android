package com.mttnow.coolestprojects.screens.fragments

import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ListView

import com.jakewharton.rxbinding.widget.RxTextView
import com.jakewharton.rxbinding.widget.TextViewTextChangeEvent
import com.mttnow.coolestprojects.R
import com.mttnow.coolestprojects.app.CoolestProjectsApp
import com.mttnow.coolestprojects.models.Summary
import com.mttnow.coolestprojects.network.CoolestProjectsService
import com.mttnow.coolestprojects.screens.adapters.ProjectsAdapter

import java.util.ArrayList

import rx.Emitter
import rx.Observable
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.functions.Action1
import rx.functions.Func1
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription

class ProjectsFragment : Fragment() {

  private val compositeSubscription = CompositeSubscription()

  private lateinit var projectsAdapter: ProjectsAdapter
  private lateinit var progressDialog: ProgressDialog
  private lateinit var coolestProjectsService: CoolestProjectsService

  private lateinit var mProjectsEt: EditText
  private lateinit var mProjectsLv: ListView

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    progressDialog = ProgressDialog(context)
    progressDialog.setMessage(getString(R.string.loading))
    coolestProjectsService = CoolestProjectsApp.get(activity).appComponent.coolestProjectsService()
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    val view = inflater.inflate(R.layout.fragment_projects, container, false)
    compositeSubscription.add(loadRxStuff())

    mProjectsEt = view.findViewById(R.id.projects_search_et) as EditText
    mProjectsLv = view.findViewById(R.id.projects_lv) as ListView

    projectsAdapter = ProjectsAdapter()
    mProjectsLv.adapter = projectsAdapter

    return view
  }

  private fun loadRxStuff(): Subscription {
    return Observable.just(null)
        .doOnNext { progressDialog.show() }
        .observeOn(Schedulers.io())
        .switchMap { coolestProjectsService.summaries(resources.getString(R.string.platform_api_url)) }
        .observeOn(AndroidSchedulers.mainThread())
        .doOnNext { progressDialog.dismiss() }
        .retryWhen { it.switchMap {

            Observable.create(Action1<Emitter<Void>> { emitter ->
              AlertDialog.Builder(context)
                  .setTitle(R.string.error)
                  .setPositiveButton(android.R.string.ok) { dialog, _ ->
                    dialog.dismiss()
                    emitter.onNext(null)
                  }
                  .setNegativeButton(android.R.string.cancel) { dialog, _ ->
                    dialog.dismiss()
                    emitter.onNext(null)
                  }.show()

            }, Emitter.BackpressureMode.DROP)
          }
        }
        .subscribe({ projects ->
          projectsAdapter.swapData(projects)
          setupListeners(projects)
        }, Throwable::printStackTrace)
  }

  private fun setupListeners(projects: List<Summary>): Subscription {
    return RxTextView.textChangeEvents(mProjectsEt)
        .subscribe { textViewTextChangeEvent ->
          filterProjects(projects, textViewTextChangeEvent.text().toString().toUpperCase())
        }
  }

  private fun filterProjects(projects: List<Summary>, s: String) {
    val filteredSummaries = projects
        .filter {
          it.name?.toUpperCase()?.contains(s) ?: false
              || it.description?.toUpperCase()?.contains(s) ?: false
              || it.coderdojo?.toUpperCase()?.contains(s) ?: false
        }
    projectsAdapter.swapData(filteredSummaries)
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if (requestCode == CATEGORY_ACTIVITY_REQUEST_CODE) {

    }
  }

  companion object {
    private val CATEGORY_ACTIVITY_REQUEST_CODE = 100012
  }
}
