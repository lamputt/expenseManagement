package com.example.expensemanagement.fragments;

import android.icu.text.DecimalFormat;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.expensemanagement.R;
import com.example.expensemanagement.sqlite_database.dao.TransactionDAO;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TotalIncomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TotalIncomeFragment extends Fragment {

    private TransactionDAO transactionDAO;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TotalIncomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TotalIncomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TotalIncomeFragment newInstance(String param1, String param2) {
        TotalIncomeFragment fragment = new TotalIncomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        transactionDAO = new TransactionDAO(requireContext());
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_total_income, container, false);
        // thay đổi totalIncome

        double totalIncome = transactionDAO.getTotalIncome();
        DecimalFormat formatter = new DecimalFormat("###,###");
        String formattedPrice = formatter.format(totalIncome);
        TextView tvTotalIncome = view.findViewById(R.id.tvSumAmount);
        tvTotalIncome.setText(formattedPrice);

        // Inflate the layout for this fragment
        return view;
    }
}