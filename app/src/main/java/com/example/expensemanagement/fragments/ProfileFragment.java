package com.example.expensemanagement.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.expensemanagement.R;
import com.example.expensemanagement.activity.AddNewCategoryActivity;
import com.example.expensemanagement.activity.ExportDataActivity;
import com.example.expensemanagement.activity.ProfileAccountActivity;
import com.example.expensemanagement.activity.ProfileCategoriesActivity;
import com.example.expensemanagement.activity.SettingActivity;
import com.example.expensemanagement.activity.SignInActivity;
import com.example.expensemanagement.sqlite_database.dao.UserDAO;
//import com.example.expensemanagement.activity.ProfileCategoriesActivity;


public class ProfileFragment extends Fragment {
    private UserDAO userDAO;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        userDAO = new UserDAO(getContext());

        LinearLayout itemAccount = rootView.findViewById(R.id.LnAccount);
        LinearLayout itemSettings  = rootView.findViewById(R.id.LnSetting);
        LinearLayout itemCategories = rootView.findViewById(R.id.LnCategory);
        LinearLayout itemExportData = rootView.findViewById(R.id.LnExportData);
        LinearLayout itemLogout = rootView.findViewById(R.id.LnLogout);

        // đổi tên theo userId
        String userName = userDAO.getUserName();
        TextView tvUserName = rootView.findViewById(R.id.tvUsername);
        tvUserName.setText(userName);

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

        itemSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity() , SettingActivity.class);
                startActivity(intent);
            }
        });

        itemExportData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity() , ExportDataActivity.class);
                startActivity(intent);
            }
        });

        itemLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomSheetLogout();
            }
        });

        return rootView;
    }
    private void showBottomSheetLogout() {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottom_shett_logout); // Gắn layout XML cho BottomSheetLogout

        Button btnNo = dialog.findViewById(R.id.btnNo);
        Button btnYes = dialog.findViewById(R.id.btnYes);

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss(); // Ẩn BottomSheet khi nhấn "No"
            }
        });

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xử lý logic khi nhấn "Yes"
                dialog.dismiss();
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear(); // Hoặc chỉ xóa cờ "isLoggedIn"
                editor.apply();
                Intent intent = new Intent(getActivity(), SignInActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                getActivity().finish();
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }
}