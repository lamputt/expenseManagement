    package com.example.expensemanagement.fragments;

    import android.os.Bundle;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.Button;
    import android.widget.ImageView;
    import android.widget.TextView;
    import android.widget.Toast;

    import androidx.annotation.NonNull;
    import androidx.annotation.Nullable;
    import androidx.fragment.app.Fragment;
    import androidx.fragment.app.FragmentTransaction;
    import androidx.recyclerview.widget.LinearLayoutManager;
    import androidx.recyclerview.widget.RecyclerView;

    import com.example.expensemanagement.R;
    import com.example.expensemanagement.Model.ItemTransaction;
    import com.example.expensemanagement.activity.MainActivity;
    import com.example.expensemanagement.adapter.ItemAdapterTransaction;
    import com.example.expensemanagement.sqlite_database.dao.BankDAO;
    import com.example.expensemanagement.sqlite_database.dao.TransactionDAO;
    import com.example.expensemanagement.sqlite_database.entities.Transaction;

    import java.text.DecimalFormat;
    import java.util.List;

    public class HomeFragment extends Fragment {
        private View redDot;
        private TransactionDAO transactionDAO;
        private ViewGroup notificationContainer;
        private Button seeAll;
        private RecyclerView recyclerView;
        private ItemAdapterTransaction adapter;
        private TextView totalIncomeTextView, totalExpenseTextView, tvSumAmount;
        private BankDAO bankDAO;
        private ImageView noTification;
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            // Gắn layout cho Fragment
            View view = inflater.inflate(R.layout.fragment_home, container, false);
            transactionDAO = new TransactionDAO(requireContext());
            bankDAO =new BankDAO(requireContext());
            redDot = view.findViewById(R.id.redDot);
            // Khởi tạo RecyclerView
            recyclerView = view.findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

            // Khởi tạo Adapter với danh sách ban đầu
            List<Transaction> transactionList = transactionDAO.getAllTransactions();
            adapter = new ItemAdapterTransaction(transactionList);
            recyclerView.setAdapter(adapter);

            // Liên kết các TextView
            totalIncomeTextView = view.findViewById(R.id.total_income);
            totalExpenseTextView = view.findViewById(R.id.total_expense);
            tvSumAmount = view.findViewById(R.id.tvSumAmount);
            noTification = view.findViewById(R.id.imgViewNotification);
            // Tính toán và cập nhật tổng tiền
            updateTotals(transactionList);

            // Xử lý nút seeAll
            seeAll = view.findViewById(R.id.btnSeeAll);
            seeAll.setOnClickListener(v -> {
                // Chuyển sang TransactionFragment
                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentHome, new TransactionFragment());
                transaction.addToBackStack(null); // Thêm vào BackStack để quay lại
                transaction.commit();
            });
            notificationContainer = view.findViewById(R.id.container_notification);
            noTification = view.findViewById(R.id.imgViewNotification);

            noTification.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Chuyển sang FragmentNotification
                    FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame_layout, new NotificationBudgetFragment()); // Chuyển đổi FrameLayout sang FragmentNotification
                    transaction.addToBackStack(null); // Thêm vào BackStack để quay lại sau
                    transaction.commit();

                    // Ẩn BottomAppBar trong MainActivity
                    MainActivity mainActivity = (MainActivity) requireActivity();
                    mainActivity.hideBottomAppBar();
                    mainActivity.hideFloatingActionButton();
                }
            });


            return view;
        }

        // Load lại dữ liệu của trang khi quay lại
        @Override
        public void onResume() {
            super.onResume();
            // Lấy lại danh sách giao dịch mới
            List<Transaction> transactionList = transactionDAO.getAllTransactions();
            adapter.updateData(transactionList); // Cập nhật lại Adapter
            updateTotals(transactionList); // Cập nhật tổng tiền
        }
        public void showRedDot() {
            redDot.setVisibility(View.VISIBLE);
        }

        // Phương thức để ẩn redDot
        public void hideRedDot() {
            redDot.setVisibility(View.GONE);
        }

        // Phương thức cập nhật tổng thu nhập, chi tiêu và số dư
        private void updateTotals(List<Transaction> transactionList) {
            double totalMoneyofBank = 0;
            double totalIncome = 0;
            double totalExpense = 0;
            for (Transaction transaction : transactionList) {
                if ("income".equals(transaction.getType())) { // So sánh đúng kiểu String
                    totalIncome += transaction.getAmount();
                } else {
                    totalExpense += transaction.getAmount();
                }
            }
            if (bankDAO != null) {
                totalMoneyofBank += bankDAO.getTotalAmount();
            }
            else {
                totalMoneyofBank = 0;
            }
            DecimalFormat formatter = new DecimalFormat("###,###");
            String formattedTotalIncome = formatter.format(totalIncome);
            String formattedTotalExpense = formatter.format(totalExpense);
            String formattedTotalSpent = formatter.format(totalMoneyofBank + totalIncome - totalExpense);
            totalIncomeTextView.setText(formattedTotalIncome);
            totalExpenseTextView.setText(formattedTotalExpense);
            tvSumAmount.setText(formattedTotalSpent);
        }

    }
