package com.example.expensemanagement.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensemanagement.R;
import com.example.expensemanagement.sqlite_database.entities.Bank;


import java.text.DecimalFormat;
import java.util.List;

public class BankAdapter extends RecyclerView.Adapter<BankAdapter.BankViewHolder> {
    private List<Bank> listBank;

    public BankAdapter(List<Bank> listBank) {
        this.listBank = listBank;
    }

    @NonNull
    @Override
    public BankViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_bank_account, parent, false);
        return new BankViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BankViewHolder holder, int position) {
        Bank bank = listBank.get(position);
        DecimalFormat formatter = new DecimalFormat("###,###");
        String formattedTotalSpent = formatter.format(bank.getAmount());
        holder.tvTotalMoney.setText(formattedTotalSpent);
        holder.tvBankName.setText(bank.getName());
    }

    @Override
    public int getItemCount() {
        return listBank.size();
    }

    public static class BankViewHolder extends RecyclerView.ViewHolder {
        TextView tvBankName, tvTotalMoney;

        public BankViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBankName = itemView.findViewById(R.id.tvBankName);
            tvTotalMoney = itemView.findViewById(R.id.tvAmountBank);
        }
    }
}
