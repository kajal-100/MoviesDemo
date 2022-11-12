package com.assignment.moviesdemo.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.Window;
import android.widget.Toast;

import com.assignment.moviesdemo.R;

public class UIUtils {

    private static Dialog dialog;

    public static Dialog showLoading(Context activity){
        dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(0));
        dialog.setContentView(R.layout.layout_progress);
        dialog.setCancelable(false);
        dialog.show();
        return dialog;
    }

    public static void hideLoading(){
        if(dialog!=null)
            dialog.dismiss();
    }

    public static void showToast(Context context, String message){
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
        toast.show();
    }
}
