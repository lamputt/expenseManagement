package com.example.expensemanagement.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.expensemanagement.R;

public class ToastUtil {

    public static void showCustomToast(Context context, String message, int iconResId) {
        // Inflate layout tùy chỉnh
        LayoutInflater inflater = LayoutInflater.from(context);
        View layout = inflater.inflate(R.layout.custom_toast, null);

        // Cập nhật nội dung TextView và ImageView
        TextView textView = layout.findViewById(R.id.toast_message);
        ImageView imageView = layout.findViewById(R.id.toast_icon);
        textView.setText(message);
        imageView.setImageResource(iconResId);

        // Tạo Toast tùy chỉnh
        Toast toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }
}

