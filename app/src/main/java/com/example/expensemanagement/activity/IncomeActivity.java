package com.example.expensemanagement.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensemanagement.R;
import com.example.expensemanagement.adapter.ItemSelectBankTransactionAdapter;
import com.example.expensemanagement.adapter.ItemSelectCategoryTransactionAdapter;
import com.example.expensemanagement.sqlite_database.dao.BankDAO;
import com.example.expensemanagement.sqlite_database.dao.CategoryDAO;
import com.example.expensemanagement.sqlite_database.dao.TransactionDAO;
import com.example.expensemanagement.sqlite_database.entities.Bank;
import com.example.expensemanagement.sqlite_database.entities.Category;
import com.example.expensemanagement.sqlite_database.entities.Transaction;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.List;

public class IncomeActivity extends AppCompatActivity {

    private EditText selectDay , selectBankAccount , selectCategory , etAmount , etdescription;
    private ImageView back;
    private Button btnContinue;
    private long selectedBankId = -1;
    private long selectedCategoryId = -1;
    private long idUser = 1;
    private String date;
    private String totalAmount;
    private Double amount;
    private String description;
    private String type = "income";
    private TransactionDAO transactionDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.IncomeActivity), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        selectBankAccount = findViewById(R.id.edtSelectCashIncome);
        selectCategory = findViewById(R.id.edtSelectCategoryIncome);
        etdescription = findViewById(R.id.edtDescripTionIncome);
        selectDay = findViewById(R.id.edtSelectDateIncome);
        btnContinue = findViewById(R.id.btnContinueExpense);
        back = findViewById(R.id.backArrowIncome);
        transactionDAO = new TransactionDAO(this);

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
                ShowdialogBankAccount();
            }
        });
        selectCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowdialogCategory();
            }
        });
        etAmount = findViewById(R.id.et_amountIncome);

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
                        Toast.makeText(IncomeActivity.this, "Không được nhập quá 9 chữ số", Toast.LENGTH_SHORT).show();
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
                if(etAmount.getText().toString().trim().isEmpty()){
                    totalAmount = "0";
                }else {
                    totalAmount = etAmount.getText().toString().trim();
                    totalAmount = totalAmount.replace(",", "");
                }
                amount = Double.parseDouble(totalAmount);
                date = selectDay.getText().toString().trim();
                description = etdescription.getText().toString().trim();

                if (totalAmount.isEmpty() || date.isEmpty() || selectedCategoryId == -1 || selectedBankId == -1 || description.isEmpty()) {
                    Toast.makeText(IncomeActivity.this, "Please fill in all information", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    // Truy vấn ngân hàng đã chọn
                    BankDAO bankDAO = new BankDAO(IncomeActivity.this);
                    Bank selectedBank = bankDAO.getBankById(selectedBankId);

                    if (selectedBank != null) {
                        double bankAmount = selectedBank.getAmount();

                        // Kiểm tra xem ngân hàng có đủ tiền hay không


                        // Cập nhật số tiền trong ngân hàng
                        double updatedAmount = bankAmount + amount;
                        ContentValues values = new ContentValues();
                        values.put("amount", updatedAmount);

                        // Sử dụng DatabaseHelper để lấy WritableDatabase
                        SQLiteDatabase db = bankDAO.getWritableDatabase();
                        db.update("banks", values, "id = ?", new String[]{String.valueOf(selectedBankId)});
                        db.close();

                        // Thêm giao dịch vào database
                        Transaction transaction = new Transaction(idUser, type, selectedCategoryId, selectedBankId, description, amount, date);
                        long result = transactionDAO.addTransaction(transaction);
                        if (result != -1) {
                            Toast.makeText(IncomeActivity.this, "Add Transaction successfully", Toast.LENGTH_SHORT).show();
                            // quay lại màn hình trước và load lại dữ liệu ở màn hình đó
                            setResult(RESULT_OK);
                            finish();
                        } else {
                            Toast.makeText(IncomeActivity.this, "Failed to save data!", Toast.LENGTH_SHORT).show();
                        }
                    }
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

    private void ShowdialogBankAccount() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottom_sheet_select_bank_transaction);

        RecyclerView recyclerView = dialog.findViewById(R.id.recyclerViewListBankSelectTransaction);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Lấy danh sách ngân hàng từ cơ sở dữ liệu
        BankDAO bankDAO = new BankDAO(this);
        List<Bank> bankList = bankDAO.getAllBanks();

        // Tạo adapter và gán cho RecyclerView
        ItemSelectBankTransactionAdapter adapter = new ItemSelectBankTransactionAdapter(bankList, new ItemSelectBankTransactionAdapter.OnBankSelectedListener() {
            @Override
            public void onBankSelected(Bank bank) {
                // Khi người dùng chọn ngân hàng
                selectBankAccount.setText(bank.getName()); // Hiển thị tên ngân hàng vào EditText
                selectedBankId = bank.getId(); // Lưu lại ID của ngân hàng đã chọn
                dialog.dismiss(); // Đóng BottomSheet
            }
        });
        recyclerView.setAdapter(adapter);

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    private void ShowdialogCategory() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottom_sheet_select_category_transaction);

        RecyclerView recyclerView = dialog.findViewById(R.id.recyclerViewListCategorySelectTransaction);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        CategoryDAO categoryDAO = new CategoryDAO(this);
        List<Category> categoryList = categoryDAO.getAllCategories();

        // Tạo adapter và gán cho RecyclerView
        ItemSelectCategoryTransactionAdapter adapter = new ItemSelectCategoryTransactionAdapter(categoryList, new ItemSelectCategoryTransactionAdapter.OnCategorySelectedListener() {
            @Override
            public void onCategorySelected(Category category) {
                // Khi người dùng chọn ngân hàng
                selectCategory.setText(category.getName()); // Hiển thị tên ngân hàng vào EditText
                selectedCategoryId = category.getId(); // Lưu lại ID của ngân hàng đã chọn
                dialog.dismiss(); // Đóng BottomSheet
            }
        });
        recyclerView.setAdapter(adapter);

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }
}