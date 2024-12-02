package com.example.expensemanagement.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.expensemanagement.R;
import com.example.expensemanagement.sqlite_database.dao.BankDAO;

import java.text.DecimalFormat;

public class AddNewWalletActivity extends AppCompatActivity {
    private ImageView back;
    private EditText selectBankAccount;
    private EditText etAmount;
    private Button btnContinue;
    private Double totalAmount;
    private BankDAO bankDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_wallet);

        btnContinue = findViewById(R.id.btnContinueAddNewWallet);
        EditText accountNumber = findViewById(R.id.edtAccountNumber);
        etAmount = findViewById(R.id.et_amount);
        bankDAO = new BankDAO(this);
        // Thêm TextWatcher để xử lý việc định dạng số tiền nhập vào
        etAmount.addTextChangedListener(new TextWatcher() {
            private String current = "";

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals(current)) {
                    etAmount.removeTextChangedListener(this);

                    String cleanString = s.toString().replaceAll("[^0-9]", "");

                    if (cleanString.length() > 9) {
                        Toast.makeText(AddNewWalletActivity.this, "Do not enter more than 9 digits", Toast.LENGTH_SHORT).show();
                        cleanString = cleanString.substring(0, 9);
                    }

                    long parsed = cleanString.isEmpty() ? 0 : Long.parseLong(cleanString);

                    // Định dạng số với dấu chấm phân cách
                    DecimalFormat formatter = new DecimalFormat("###,###");
                    String formatted = formatter.format(parsed);

                    current = formatted;
                    etAmount.setText(formatted); // Hiển thị số đã định dạng
                    etAmount.setSelection(formatted.length()); // Đảm bảo con trỏ ở cuối

                    etAmount.addTextChangedListener(this);
                }
            }
        });

        btnContinue.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//                bankDAO.resetDataInBanks();
                String bankName = selectBankAccount.getText().toString().trim();
                String accountNum = accountNumber.getText().toString().trim();
                String amount = etAmount.getText().toString().replaceAll("[^0-9]", "");
                amount = amount.replace("," , "");
                totalAmount = Double.parseDouble(amount);

                if (bankName.isEmpty() || accountNum.isEmpty() || amount.isEmpty()) {
                    Toast.makeText(AddNewWalletActivity.this, "Please fill in all information", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (bankDAO.isBankExist(bankName)) {
                    Toast.makeText(AddNewWalletActivity.this, "Bank already exists. Please choose another bank.", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    // Debug log để kiểm tra
                    System.out.println("Bank không tồn tại, tiếp tục thêm...");
                }

                long result = bankDAO.addBank(bankName, accountNum, totalAmount);
                if (result != -1) {
                    Toast.makeText(AddNewWalletActivity.this, "Add bank successfully", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(AddNewWalletActivity.this, "Failed to save data!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        back = findViewById(R.id.backArrowAddNewWallet);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Xử lý việc lấy chọn ngân hàng
        selectBankAccount = findViewById(R.id.edtSelectCash);
        selectBankAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddNewWalletActivity.this, "Expense click", Toast.LENGTH_SHORT).show();
                Showdialog();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.AddNewWalletActivity), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void Showdialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottomshett_select_bank);

        LinearLayout lnViettin = dialog.findViewById(R.id.lnViettinbank);
        LinearLayout lnAgribank = dialog.findViewById(R.id.lnAgribank);
        LinearLayout lnDongA = dialog.findViewById(R.id.lnDongAbank);
        LinearLayout lnMb = dialog.findViewById(R.id.lnMbBank);

        lnViettin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectBankAccount.setText("ViettinBank"); // Chọn ViettinBank và hiển thị trong EditText
                dialog.dismiss();
            }
        });

        lnAgribank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectBankAccount.setText("Agribank"); // Chọn Agribank và hiển thị trong EditText
                dialog.dismiss();
            }
        });

        lnDongA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectBankAccount.setText("DongABank"); // Chọn DongA và hiển thị trong EditText
                dialog.dismiss();
            }
        });

        lnMb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectBankAccount.setText("MBBank"); // Chọn MB Bank và hiển thị trong EditText
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }
}
