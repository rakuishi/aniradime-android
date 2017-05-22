package com.rakuishi.postalcode.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.rakuishi.postalcode.R;
import com.rakuishi.postalcode.databinding.ViewEmptyBinding;

public class EmptyView extends RelativeLayout {

    ViewEmptyBinding binding;

    public EmptyView(Context context) {
        super(context);
        if (!isInEditMode()) {
            initView(context);
        }
    }

    public EmptyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            initView(context);
        }
    }

    public EmptyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (!isInEditMode()) {
            initView(context);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public EmptyView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        if (!isInEditMode()) {
            initView(context);
        }
    }

    public void initView(Context context) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.view_empty, this, true);
    }

    public void setDrawable(@DrawableRes int resId) {
        binding.imageView.setImageDrawable(getResources().getDrawable(resId));
    }

    public void setText(@StringRes int resId) {
        binding.textView.setText(resId);
    }
}
