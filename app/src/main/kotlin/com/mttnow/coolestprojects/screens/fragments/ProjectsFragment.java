package com.mttnow.coolestprojects.screens.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.mttnow.coolestprojects.R;
import com.mttnow.coolestprojects.models.Summary;
import com.mttnow.coolestprojects.screens.CategoryActivity;
import com.mttnow.coolestprojects.screens.adapters.ProjectsAdapter;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class ProjectsFragment extends BaseFragment {

    private EditText mProjectsEt;
    private ImageView mFilterCategoryBtn;
    private ListView mProjectsLv;

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
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        coolestProjectsService.summaries()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Summary>>() {
                    @Override
                    public void call(List<Summary> projects) {
                        mProjectsLv.setAdapter(new ProjectsAdapter(projects));
                        setupListeners(projects);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });

    }

    private void setupListeners(final List<Summary> projects) {

        mProjectsEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                filterProjects(projects, editable.toString().toUpperCase());
            }
        });

        mFilterCategoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CategoryActivity.class);
                getActivity().startActivityForResult(intent, 100);
            }
        });
    }

    private void filterProjects(List<Summary> projects, String s) {
        List<Summary> filteredSummaries = new ArrayList<>();
        for (int i=0; i < projects.size(); i++) {
            Summary p = projects.get(i);
            if (p.getName() != null && p.getName().toUpperCase().contains(s) ||
                    p.getDescription() != null && p.getDescription().toUpperCase().contains(s) ||
                    p.getCoderdojo() != null && p.getCoderdojo().toUpperCase().contains(s)) {
                filteredSummaries.add(p);
            }
        }
        mProjectsLv.setAdapter(new ProjectsAdapter(filteredSummaries));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}
