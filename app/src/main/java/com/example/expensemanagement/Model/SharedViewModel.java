package com.example.expensemanagement.Model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    private final MutableLiveData<Boolean> showRedDot = new MutableLiveData<>(false); // Mặc định là không hiển thị

    public LiveData<Boolean> getShowRedDot() {
        return showRedDot;
    }

    public void updateRedDotStatus(boolean show) {
        showRedDot.setValue(show);
    }

    public void markNotificationsAsSeen() {
        showRedDot.setValue(false); // Ẩn redDot sau khi thông báo được xem
    }
}
