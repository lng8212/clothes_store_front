package com.example.e_commerce.utils;


import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.example.e_commerce.R;


public class ProgressDialog {
    Activity activity;
    public ProgressDialog(Activity activity){
         this.activity = activity;
    }

    AlertDialog dialog;
    public void startDialog() {
        View mDialogView = LayoutInflater.from(activity).inflate(R.layout.progress_view, null);

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(activity).setView(mDialogView);
        dialog = mBuilder.show();
        dialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(activity, R.drawable.bg_transparent_view));
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
    }

    public void  dismissDialog() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }
}
