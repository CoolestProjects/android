package com.mttnow.coolestprojects.screens.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.jakewharton.rxbinding.widget.TextViewTextChangeEvent;
import com.mttnow.coolestprojects.R;
import com.mttnow.coolestprojects.models.Summary;
import com.mttnow.coolestprojects.screens.CategoryActivity;
import com.mttnow.coolestprojects.screens.adapters.ProjectsAdapter;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class ProjectsFragment extends BaseFragment {

  private static final int CATEGORY_ACTIVITY_REQUEST_CODE = 100012;
  private EditText mProjectsEt;
  private ImageView mFilterCategoryBtn;
  private ListView mProjectsLv;
  private ProjectsAdapter projectsAdapter;

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View contentView = inflater.inflate(R.layout.fragment_projects, container, false);
    return contentView;
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    mProjectsEt = (EditText) view.findViewById(R.id.projects_search_et);
    mFilterCategoryBtn = (ImageView) view.findViewById(R.id.filter_category_btn);
    mProjectsLv = (ListView) view.findViewById(R.id.projects_lv);

    projectsAdapter = new ProjectsAdapter();
    mProjectsLv.setAdapter(projectsAdapter);
  }

  @Override
  public void onStart() {
    super.onStart();
    addSubscription(Observable.just(null)
        .doOnNext(showLoadingRX())
        .observeOn(Schedulers.io())
        .switchMap(new Func1<Object, Observable<List<Summary>>>() {
          @Override
          public Observable<List<Summary>> call(Object o) {
            return coolestProjectsService.summaries("");
          }
        })
        .observeOn(AndroidSchedulers.mainThread())
        .doOnNext(hideLoadingRX())
        .subscribe(new Action1<List<Summary>>() {
          @Override
          public void call(List<Summary> projects) {
            projectsAdapter.swapData(projects);
            setupListeners(projects);
          }
        }, new Action1<Throwable>() {
          @Override
          public void call(Throwable throwable) {
            throwable.printStackTrace();
          }
        }));
  }

  private void setupListeners(final List<Summary> projects) {

    addSubscription(RxTextView.textChangeEvents(mProjectsEt)
        .subscribe(new Action1<TextViewTextChangeEvent>() {
          @Override
          public void call(TextViewTextChangeEvent textViewTextChangeEvent) {
            filterProjects(projects, textViewTextChangeEvent.text());
          }
        }));

    addSubscription(RxView.clicks(mFilterCategoryBtn).subscribe(new Action1<Void>() {
      @Override
      public void call(Void aVoid) {
        Intent intent = new Intent(getActivity(), CategoryActivity.class);
        getActivity().startActivityForResult(intent, CATEGORY_ACTIVITY_REQUEST_CODE);
      }
    }));
  }

  private void filterProjects(List<Summary> projects, CharSequence s) {
    List<Summary> filteredSummaries = new ArrayList<>();
    for (int i = 0; i < projects.size(); i++) {
      Summary p = projects.get(i);
      if (p.getName() != null && p.getName().toUpperCase().contains(s) ||
          p.getDescription() != null && p.getDescription().toUpperCase().contains(s) ||
          p.getCoderdojo() != null && p.getCoderdojo().toUpperCase().contains(s)) {
        filteredSummaries.add(p);
      }
    }
    projectsAdapter.swapData(filteredSummaries);
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == CATEGORY_ACTIVITY_REQUEST_CODE) {

    }

  }
}
