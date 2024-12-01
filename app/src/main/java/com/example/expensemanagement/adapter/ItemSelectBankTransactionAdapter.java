package com.example.expensemanagement.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensemanagement.R;

import com.example.expensemanagement.sqlite_database.entities.Bank;


import java.util.List;

public class ItemSelectBankTransactionAdapter extends RecyclerView.Adapter<ItemSelectBankTransactionAdapter.BankViewHolder> {

    private List<Bank> bankList;
    private OnBankSelectedListener listener;

    // Constructor
    public ItemSelectBankTransactionAdapter(List<Bank> bankList, OnBankSelectedListener listener) {
        this.bankList = bankList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public BankViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_bank_transaction, parent, false);
        return new BankViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BankViewHolder holder, int position) {
        Bank bank = bankList.get(position);
        holder.tvBankName.setText(bank.getName());

        holder.itemView.setOnClickListener(v -> {
            // Khi người dùng chọn ngân hàng, gọi callback và truyền ngân hàng đã chọn
            listener.onBankSelected(bank);
        });
    }

    @Override
    public int getItemCount() {
        return bankList.size();
    }

    public static class BankViewHolder extends RecyclerView.ViewHolder {
        TextView tvBankName;

        public BankViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBankName = itemView.findViewById(R.id.nameBankTransaction);
        }
    }

    // Interface callback để khi chọn ngân hàng thì trả về thông tin ngân hàng đã chọn
    public interface OnBankSelectedListener {
        void onBankSelected(Bank bank);
    }
}

