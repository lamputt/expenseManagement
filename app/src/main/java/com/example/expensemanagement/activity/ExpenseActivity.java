package com.example.expensemanagement.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.expensemanagement.R;

import java.text.DecimalFormat;
import java.util.Calendar;

public class ExpenseActivity extends AppCompatActivity {

    private EditText selectDay;
    private ImageView back;
    private EditText selectBankAccount;
    private EditText etAmount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.ExpenseActivity), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        selectBankAccount = findViewById(R.id.edtSelectCashExpense);
        selectDay = findViewById(R.id.edtSelectDate);
        back = findViewById(R.id.backArrowExpense);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        selectDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        selectBankAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Showdialog();
            }
        });
        etAmount = findViewById(R.id.et_amountExpense);

        // Thêm TextWatcher để xử lý việc định dạng số tiền nhập vào
        etAmount.addTextChangedListener(new TextWatcher() {
            private String current = "";

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals(current)) {
                    etAmount.removeTextChangedListener(this);

                    String cleanString = s.toString().replaceAll("[^0-9]", "");

                    if (cleanString.length() > 9) {
                        Toast.makeText(ExpenseActivity.this, "Không được nhập quá 9 chữ số", Toast.LENGTH_SHORT).show();
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

    }

    private void showDatePicker() {
        // Lấy ngày hiện tại
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Hiển thị DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (DatePicker view, int selectedYear, int selectedMonth, int selectedDay) -> {
                    // Định dạng ngày (Tháng bắt đầu từ 0 nên cần +1)
                    String date = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                    selectDay.setText(date); // Hiển thị ngày vào EditText
                },
                year, month, day);

        datePickerDialog.show();
    }

    private void Showdialog () {
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
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);

    }
}