//package com.example.expensemanagement.adapter;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.expensemanagement.Model.BankAccount;
//import com.example.expensemanagement.R;
//
//import java.text.DecimalFormat;
//import java.util.ArrayList;
//
//public class BankAccountAdapter extends RecyclerView.Adapter<BankAccountAdapter.BankAccountViewHolder> {
//    private ArrayList<BankAccount> bankAccounts;
//
//    public BankAccountAdapter(ArrayList<BankAccount> bankAccounts) {
//        this.bankAccounts = bankAccounts;
//    }
//
//    @NonNull
//    @Override
//    public BankAccountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bank_account, parent, false);
//        return new BankAccountViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull BankAccountViewHolder holder, int position) {
//        BankAccount bankAccount = bankAccounts.get(position);
//
//        // Định dạng số tiền với dấu chấm phân cách
//        DecimalFormat formatter = new DecimalFormat("###,###");
//        String formattedAmount = formatter.format(bankAccount.getMoney());
//
//        holder.tvBankName.setText(bankAccount.getName());
//        holder.tvAmount.setText(formattedAmount); // Hiển thị số tiền đã định dạng
//    }
//
//    @Override
//    public int getItemCount() {
//        return bankAccounts.size();
//    }
//
//    public static class BankAccountViewHolder extends RecyclerView.ViewHolder {
//        TextView tvBankName, tvAmount;
//        ImageView imageView;
//
//        public BankAccountViewHolder(@NonNull View itemView) {
//            super(itemView);
//            tvBankName = itemView.findViewById(R.id.tvBankName);
//            tvAmount = itemView.findViewById(R.id.tvAmount);
//            imageView = itemView.findViewById(R.id.imageView); // Giữ nguyên hình ảnh
//        }
//    }
//}
