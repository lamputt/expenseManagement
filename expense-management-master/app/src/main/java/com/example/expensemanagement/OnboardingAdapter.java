package com.example.expensemanagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.viewpager.widget.PagerAdapter;
import org.jetbrains.annotations.NotNull;

public class OnboardingAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private int[] layouts;

    public OnboardingAdapter(Context context, int[] layouts) {
        this.context = context;
        this.layouts = layouts;
    }

    @Override
    public int getCount() {
        return layouts.length;
    }

    @Override
    public boolean isViewFromObject(@NotNull View view, @NotNull Object obj) {
        return view == obj;
    }

    @NotNull
    @Override
    public Object instantiateItem(@NotNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(layouts[position], container, false);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, @NotNull Object object) {
        View view = (View) object;
        container.removeView(view);
    }
}

