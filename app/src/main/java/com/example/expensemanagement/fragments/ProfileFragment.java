package com.example.expensemanagement.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.expensemanagement.R;
import com.example.expensemanagement.activity.AddNewCategoryActivity;
import com.example.expensemanagement.activity.ProfileAccountActivity;
import com.example.expensemanagement.activity.ProfileCategoriesActivity;


public class ProfileFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        LinearLayout itemAccount = rootView.findViewById(R.id.LnAccount);
        LinearLayout itemSettings  = rootView.findViewById(R.id.LnSetting);
        LinearLayout itemCategories = rootView.findViewById(R.id.LnCategory);
        LinearLayout itemExportData = rootView.findViewById(R.id.LnExportData);

        itemAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity() , ProfileAccountActivity.class);
                startActivity(intent);
            }
        });

        itemCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity() , ProfileCategoriesActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }
}