package ru.vinogradov.hotelexample;


import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

public class ToolTip {

    Context context;
    private View contentView;
    private LayoutInflater inflater;

    public ToolTip(Context context) {
        this.context = context;
        inflater = (LayoutInflater)context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        contentView = inflater.inflate(R.layout.tooltip, null);
    }

    public void show(View v) {

        PopupWindow toolTip = new PopupWindow(context);
        toolTip.setContentView(contentView);
        toolTip.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        toolTip.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        toolTip.setFocusable(true);
        toolTip.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        int[] location = new int[2];

        v.getLocationOnScreen(location);

        contentView.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        toolTip.showAtLocation(v, Gravity.NO_GRAVITY, location[0]  - contentView.getMeasuredWidth(), location[1]);

    }

}
