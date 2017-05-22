package com.rakuishi.postalcode.view;

import android.content.Context;
import android.graphics.Point;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

public class NendHelper {

    public NendHelper() {

    }

    public static void setPadding(Context context, View view) {
        view.setPadding(
                view.getPaddingLeft(),
                view.getPaddingTop(),
                view.getPaddingRight(),
                view.getPaddingBottom() + NendHelper.getNendHeight(context)
        );

        if (view instanceof RecyclerView) {
            ((RecyclerView) view).setClipToPadding(false);
        }
    }

    public static void setMargin(Context context, View view) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
        marginLayoutParams.setMargins(
                marginLayoutParams.leftMargin,
                marginLayoutParams.topMargin,
                marginLayoutParams.rightMargin,
                marginLayoutParams.bottomMargin + NendHelper.getNendHeight(context)
        );
        view.setLayoutParams(marginLayoutParams);
    }

    public static int getNendHeight(Context context) {
        int width = getDisplaySize(context).x;
        int height = width / 320 * 50;
        return height;
    }

    private static Point getDisplaySize(Context context) {
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        return point;
    }
}
